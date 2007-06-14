#!/bin/bash

set -x

DIRNAME=`dirname $0`
cd ${DIRNAME}
source init.sh

###################################################################

# Initialization

SRCROOT="${NB_ALL}"

CACHEROOT="${BASE_DIR}/cache"
AS_ROOT="${BASE_DIR}/SUNWappserver"

J2EE_HOME="${BASE_DIR}/SUNWappserver/glassfish"
TESTROOT="${SRCROOT}/visualweb/test"

###################################################################

# Download Application Server
if [ ! -d ${CACHEROOT} ]; then
	mkdir -p $CACHEROOT
fi

if [ -f ${AS_KITSERVER}/${AS_BINARY} ]; then
    cp ${AS_KITSERVER}/${AS_BINARY} ${CACHEROOT}
    ERROR_CODE=$?
else
    echo "ERROR: Please set AS_KITSERVER and AS_BINARY - ${AS_KITSERVER}/${AS_BINARY}"
    exit 2;
fi

if [ $ERROR_CODE != 0 ]; then
    echo "ERROR: $ERROR_CODE - Can't download Glassfish"
    exit $ERROR_CODE;
fi

###################################################################

# First be good and uninstall
if [ -x $J2EE_HOME/bin/uninstall ]; then
    $J2EE_HOME/bin/uninstall -silent
fi
# This is a temp hack as the "uninstall" command is broken
if [ -d ${J2EE_HOME} -a ! -z ${J2EE_HOME} ]; then
	rm -rf ${J2EE_HOME}
fi

###################################################################

# Creating statefile
rm -f ${AS_ROOT}/sunappserver_statefile
echo "A" > ${AS_ROOT}/sunappserver_statefile

###################################################################

# Install Application Server
if [ ! -d ${AS_ROOT} ]; then
	mkdir ${AS_ROOT}
fi

chmod a+x ${CACHEROOT}/${AS_BINARY}
cd ${AS_ROOT}

TEMP_DISPLAY="${DISPLAY}"
unset DISPLAY

java -Xmx256m -jar ${CACHEROOT}/${AS_BINARY} < ${AS_ROOT}/sunappserver_statefile

ERROR_CODE=$?
if [ $ERROR_CODE != 0 ]; then
    echo "ERROR: $ERROR_CODE - Can't install Glassfish"
    exit $ERROR_CODE;
fi

DISPLAY="${TEMP_DISPLAY}"
export DISPLAY

###################################################################

# Setup Application Server
cd ${J2EE_HOME}
ant -f setup.xml -Dinstance.port=28080

###################################################################

# Setup properties file 
cp $TESTROOT/data/DefaultDeploymentTargets.properties.template $TESTROOT/data/tmp.properties
MODIFIED_J2EE_HOME=`echo ${J2EE_HOME} | sed 's/\//::/g'`
sed -e "s/J2EE_HOME/${MODIFIED_J2EE_HOME}/g" -e "s/::/\//g" -e "s/8080/28080/g" $TESTROOT/data/tmp.properties > $TESTROOT/data/DefaultDeploymentTargets.properties
   
ERROR_CODE=$?
if [ $ERROR_CODE != 0 ]; then
	echo "ERROR: $ERROR_CODE - Can't setup Glassfish"
	exit $ERROR_CODE;
fi
    
rm -f $TESTROOT/data/tmp.properties

###################################################################

# Run Sanity test on VisualWeb build
cd $SRCROOT/visualweb/ravebuild
ant build-test-tools -Dnetbeans.dist.dir="${J2EE_HOME}"
ant commit-validation -Dnetbeans.dist.dir="${J2EE_HOME}"

ERROR_CODE=$?
if [ $ERROR_CODE != 0 ]; then
    echo "ERROR: $ERROR_CODE - Error in running sanity test"
    exit $ERROR_CODE;
fi

###################################################################

# Clean up
if [ -d ${CACHEROOT} -a ! -z ${CACHEROOT} ]; then
	rm -rf ${CACHEROOT}
fi

######################################## END #######################################
