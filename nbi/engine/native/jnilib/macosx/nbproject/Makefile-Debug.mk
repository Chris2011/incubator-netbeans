#
# Gererated Makefile - do not edit!
#
# Edit the Makefile in the project folder instead (../Makefile). Each target
# has a -pre and a -post target defined where you can add custumized code.
#
# This makefile implements configuration specific macros and targets.


# Environment
MKDIR=mkdir
CP=cp
CCADMIN=CCadmin
RANLIB=ranlib
CC=gcc
CCC=g++
CXX=g++
FC=

# Include project Makefile
include Makefile

# Object Directory
OBJECTDIR=build/Debug/GNU-Generic

# Object Files
OBJECTFILES= \
	${OBJECTDIR}/_ext/Users/lipin/tmp/nbi/engine/native/jnilib/macosx/../.common/src/CommonUtils.o \
	${OBJECTDIR}/_ext/Users/lipin/tmp/nbi/engine/native/jnilib/macosx/../.unix/src/jni_UnixNativeUtils.o

# C Compiler Flags
CFLAGS=-arch i386 -arch ppc -isysroot /Developer/SDKs/MacOSX10.4u.sdk -dynamiclib

# CC Compiler Flags
CCFLAGS=-arch i386 -arch ppc -isysroot /Developer/SDKs/MacOSX10.4u.sdk -dynamiclib
CXXFLAGS=-arch i386 -arch ppc -isysroot /Developer/SDKs/MacOSX10.4u.sdk -dynamiclib

# Fortran Compiler Flags
FFLAGS=

# Link Libraries and Options
LDLIBSOPTIONS=

# Build Targets
.build-conf: ${BUILD_SUBPROJECTS} dist/macosx.dylib

dist/macosx.dylib: ${OBJECTFILES}
	${MKDIR} -p dist
	${LINK.c} -Wl,-syslibroot /Developer/SDKs/MacOSX10.4u.sdk -arch i386 -arch ppc -dynamiclib -o dist/macosx.dylib ${OBJECTFILES} ${LDLIBSOPTIONS} 

${OBJECTDIR}/_ext/Users/lipin/tmp/nbi/engine/native/jnilib/macosx/../.common/src/CommonUtils.o: ../.common/src/CommonUtils.c 
	${MKDIR} -p ${OBJECTDIR}/_ext/Users/lipin/tmp/nbi/engine/native/jnilib/macosx/../.common/src
	$(COMPILE.c) -s -I/usr/java/include -o ${OBJECTDIR}/_ext/Users/lipin/tmp/nbi/engine/native/jnilib/macosx/../.common/src/CommonUtils.o ../.common/src/CommonUtils.c

${OBJECTDIR}/_ext/Users/lipin/tmp/nbi/engine/native/jnilib/macosx/../.unix/src/jni_UnixNativeUtils.o: ../.unix/src/jni_UnixNativeUtils.c 
	${MKDIR} -p ${OBJECTDIR}/_ext/Users/lipin/tmp/nbi/engine/native/jnilib/macosx/../.unix/src
	$(COMPILE.c) -s -I/usr/java/include -o ${OBJECTDIR}/_ext/Users/lipin/tmp/nbi/engine/native/jnilib/macosx/../.unix/src/jni_UnixNativeUtils.o ../.unix/src/jni_UnixNativeUtils.c

# Subprojects
.build-subprojects:

# Clean Targets
.clean-conf:
	${RM} -r build/Debug
	${RM} dist/macosx.dylib

# Subprojects
.clean-subprojects:
