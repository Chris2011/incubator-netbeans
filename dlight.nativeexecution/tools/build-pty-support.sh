#!/bin/sh

cd `dirname $0`
MYDIR=`pwd`

WDIR=/tmp/${USER}/ps$$
rm -rf ${WDIR}
mkdir -p ${WDIR}
LWDIR=/tmp/${USER}/lps$$
rm -rf ${LWDIR}
mkdir -p ${LWDIR}
LHOST=`hostname`.`cat /etc/resolv.conf | grep domain| sed 's/domain \(.*\)/\1/'`
LUSER=$USER

build() {
   HOST=$1
   shift
   CONFIGS=$@

   printf "Building ${CONFIGS} on host ${HOST} ... "
   do_build $HOST $CONFIGS
   if [ $? -eq 0 ]; then
      echo OK
   else
      echo FAIL
      exit 1
   fi
}

do_build() {
   HOST=$1
   shift
   CONFIGS=$@

   cd ${MYDIR}
   tar cf ${LWDIR}/ps.tar ./PtySupport >> build.log 2>&1
   cat << EOF | ssh ${HOST} sh -s 
   rm -rf ${WDIR}
   mkdir -p ${WDIR}
EOF

   scp ${LWDIR}/ps.tar ${HOST}:${WDIR} >> build.log 2>&1

   cat << EOF | ssh ${HOST} sh -s 
   cd ${WDIR}
   tar xf ./ps.tar || return 1 
   cd PtySupport
   PATH=${PATH}:/usr/ccs/bin
   export PATH

   for i in ${CONFIGS}; do
      make CONF=\$i >> build.log 2>&1 || return 1
   done
   cd dist
   scp -r * ${LUSER}@${LHOST}:${LWDIR} >> build.log 2>&1
EOF
}

copy_to_release() {
   echo Copying executables...
   set -x
   cp ${LWDIR}/Solaris_x64/GNU-Solaris-x86/ptysupport ../release/bin/nativeexecution/SunOS-x86_64/pty
   cp ${LWDIR}/Solaris_x86/GNU-Solaris-x86/ptysupport ../release/bin/nativeexecution/SunOS-x86/pty
   cp ${LWDIR}/MacOS_x64/GNU-MacOSX/ptysupport ../release/bin/nativeexecution/MacOSX-x86_64/pty
   cp ${LWDIR}/MacOS_x86/GNU-MacOSX/ptysupport ../release/bin/nativeexecution/MacOSX-x86/pty
   cp ${LWDIR}/Linux_x86/GNU-Linux-x86/ptysupport ../release/bin/nativeexecution/Linux-x86/pty
   cp ${LWDIR}/Linux_x64/GNU-Linux-x86/ptysupport ../release/bin/nativeexecution/Linux-x86_64/pty
   cp ${LWDIR}/Linux_sparc64/GNU-Linux-Sparc/ptysupport ../release/bin/nativeexecution/Linux-sparc_64/pty
   cp ${LWDIR}/Solaris_sparc/GNU-Solaris-Sparc/ptysupport ../release/bin/nativeexecution/SunOS-sparc/pty
   cp ${LWDIR}/Solaris_sparc64/GNU-Solaris-Sparc/ptysupport ../release/bin/nativeexecution/SunOS-sparc_64/pty
   # Only 32-bit version for Windows...
   cp ${LWDIR}/Windows_x86/Cygwin-Windows/ptysupport.exe ../release/bin/nativeexecution/Windows-x86/pty
   cp ${LWDIR}/Windows_x86/Cygwin-Windows/ptysupport.exe ../release/bin/nativeexecution/Windows-x86_64/pty
}


