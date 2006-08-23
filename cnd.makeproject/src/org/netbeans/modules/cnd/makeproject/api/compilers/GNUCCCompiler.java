/*
 * The contents of this file are subject to the terms of the Common Development
 * and Distribution License (the License). You may not use this file except in
 * compliance with the License.
 *
 * You can obtain a copy of the License at http://www.netbeans.org/cddl.html
 * or http://www.netbeans.org/cddl.txt.

 * When distributing Covered Code, include this CDDL Header Notice in each file
 * and include the License file at http://www.netbeans.org/cddl.txt.
 * If applicable, add the following below the CDDL Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * The Original Software is NetBeans. The Initial Developer of the Original
 * Software is Sun Microsystems, Inc. Portions Copyright 1997-2006 Sun
 * Microsystems, Inc. All Rights Reserved.
 */

package org.netbeans.modules.cnd.makeproject.api.compilers;

import java.util.List;
import java.util.Vector;
import org.netbeans.modules.cnd.makeproject.api.platforms.Platform;

public class GNUCCCompiler extends CCCCompiler {

    /** Replace this temporary stuff */
    private static final boolean fullIncludes = true; // Boolean.getBoolean("gcc.full.includes");

    private static final String[] DEVELOPMENT_MODE_OPTIONS = {
	"",  // Fast Build // NOI18N
	"-g", // Debug" // NOI18N
	"-g -O", // Performance Debug" // NOI18N
	"-g", // Test Coverage // NOI18N
	"-g -O2", // Dianosable Release // NOI18N
	"-O2", // Release // NOI18N
	"-O3", // Performance Release // NOI18N
    };
    
    protected static final String[] WARNING_LEVEL_OPTIONS = {
	"-w", // No Warnings // NOI18N
	"", // Default // NOI18N
	"-Wall", // More Warnings // NOI18N
	"-Werror", // Convert Warnings to Errors // NOI18N
    };
    
    /** Creates a new instance of GNUCCompiler */
    public GNUCCCompiler() {
        super(CCCompiler, "g++", "GNU C++ Compiler"); // NOI18N
    }
    
    public String getDevelopmentModeOptions(int value) {
        return DEVELOPMENT_MODE_OPTIONS[value];
    }
    
    public String getWarningLevelOptions(int value) {
        if (value < WARNING_LEVEL_OPTIONS.length)
            return WARNING_LEVEL_OPTIONS[value];
        else
            return ""; // NOI18N
    }
    
    public String getSixtyfourBitsOption(boolean value) {
        return value ? "-m64" : ""; // NOI18N
    }
    
    public String getStripOption(boolean value) {
        return value ? "-s" : ""; // NOI18N
    }
    
    public List getSystemPreprocessorSymbols(Platform platform) {
        Vector list = new Vector();
        // FIXUP: replace this hard-coded implementation to a proper one
        if (platform.getId() == Platform.PLATFORM_SOLARIS_INTEL || platform.getId() == Platform.PLATFORM_SOLARIS_SPARC) {
            list.add("__DBL_MIN_EXP__=(-1021)");
            list.add("__EXTENSIONS__=1");
            list.add("__FLT_MIN__=1.17549435e-38F");
            list.add("__CHAR_BIT__=8");
            list.add("_XOPEN_SOURCE=500");
            list.add("__WCHAR_MAX__=2147483647");
            list.add("__DBL_DENORM_MIN__=4.9406564584124654e-324");
            list.add("__FLT_EVAL_METHOD__=2");
            list.add("__DBL_MIN_10_EXP__=(-307)");
            list.add("__FINITE_MATH_ONLY__=0");
            list.add("__GNUC_PATCHLEVEL__=3");
            list.add("__SHRT_MAX__=32767");
            list.add("__LDBL_MAX__=1.18973149535723176502e+4932L");
            list.add("__unix=1");
            list.add("__LDBL_MAX_EXP__=16384");
            list.add("__SCHAR_MAX__=127");
            list.add("__USER_LABEL_PREFIX__");
            list.add("__STDC_HOSTED__=1");
            list.add("_LARGEFILE64_SOURCE=1");
            list.add("__LDBL_HAS_INFINITY__=1");
            list.add("__DBL_DIG__=15");
            list.add("__FLT_EPSILON__=1.19209290e-7F");
            list.add("__GXX_WEAK__=1");
            list.add("__LDBL_MIN__=3.36210314311209350626e-4932L");
            list.add("__unix__=1");
            list.add("__DECIMAL_DIG__=21");
            list.add("_LARGEFILE_SOURCE=1");
            list.add("__LDBL_HAS_QUIET_NAN__=1");
            list.add("__GNUC__=3");
            list.add("__DBL_MAX__=1.7976931348623157e+308");
            list.add("__DBL_HAS_INFINITY__=1");
            list.add("__SVR4=1");
            list.add("__cplusplus=1");
            list.add("__DEPRECATED=1");
            list.add("__DBL_MAX_EXP__=1024");
            list.add("__GNUG__=3");
            list.add("__LONG_LONG_MAX__=9223372036854775807LL");
            list.add("__GXX_ABI_VERSION=1002");
            list.add("__FLT_MIN_EXP__=(-125)");
            list.add("__DBL_MIN__=2.2250738585072014e-308");
            list.add("__FLT_MIN_10_EXP__=(-37)");
            list.add("__DBL_HAS_QUIET_NAN__=1");
            list.add("__tune_i386__=1");
            list.add("__sun=1");
            list.add("__REGISTER_PREFIX__");
            list.add("__NO_INLINE__=1");
            list.add("__i386=1");
            list.add("__FLT_MANT_DIG__=24");
            list.add("__VERSION__=\"3.4.3 (csl-sol210-3_4-branch+sol_rpath)\"");
            list.add("i386=1");
            list.add("sun=1");
            list.add("unix=1");
            list.add("__i386__=1");
            list.add("__SIZE_TYPE__=unsigned int");
            list.add("__ELF__=1");
            list.add("__FLT_RADIX__=2");
            list.add("__LDBL_EPSILON__=1.08420217248550443401e-19L");
            list.add("__FLT_HAS_QUIET_NAN__=1");
            list.add("__FLT_MAX_10_EXP__=38");
            list.add("__LONG_MAX__=2147483647L");
            list.add("__FLT_HAS_INFINITY__=1");
            list.add("__PRAGMA_REDEFINE_EXTNAME=1");
            list.add("__EXCEPTIONS=1");
            list.add("__LDBL_MANT_DIG__=64");
            list.add("__WCHAR_TYPE__=long int");
            list.add("__FLT_DIG__=6");
            list.add("__INT_MAX__=2147483647");
            list.add("__FLT_MAX_EXP__=128");
            list.add("__DBL_MANT_DIG__=53");
            list.add("__WINT_TYPE__=long int");
            list.add("__LDBL_MIN_EXP__=(-16381)");
            list.add("__LDBL_MAX_10_EXP__=4932");
            list.add("__DBL_EPSILON__=2.2204460492503131e-16");
            list.add("__sun__=1");
            list.add("__svr4__=1");
            list.add("__FLT_DENORM_MIN__=1.40129846e-45F");
            list.add("__FLT_MAX__=3.40282347e+38F");
            list.add("__GNUC_MINOR__=4");
            list.add("__DBL_MAX_10_EXP__=308");
            list.add("__LDBL_DENORM_MIN__=3.64519953188247460253e-4951L");
            list.add("__PTRDIFF_TYPE__=int");
            list.add("__LDBL_MIN_10_EXP__=(-4931)");
            list.add("__LDBL_DIG__=18");
        }
        else if (platform.getId() == Platform.PLATFORM_LINUX) {
            // FIXUP: for now it's just the same except for "sun" is undefined
            list.add("__DBL_MIN_EXP__=(-1021)");
            list.add("__EXTENSIONS__=1");
            list.add("__FLT_MIN__=1.17549435e-38F");
            list.add("__CHAR_BIT__=8");
            list.add("_XOPEN_SOURCE=500");
            list.add("__WCHAR_MAX__=2147483647");
            list.add("__DBL_DENORM_MIN__=4.9406564584124654e-324");
            list.add("__FLT_EVAL_METHOD__=2");
            list.add("__DBL_MIN_10_EXP__=(-307)");
            list.add("__FINITE_MATH_ONLY__=0");
            list.add("__GNUC_PATCHLEVEL__=3");
            list.add("__SHRT_MAX__=32767");
            list.add("__LDBL_MAX__=1.18973149535723176502e+4932L");
            list.add("__unix=1");
            list.add("__LDBL_MAX_EXP__=16384");
            list.add("__SCHAR_MAX__=127");
            list.add("__USER_LABEL_PREFIX__");
            list.add("__STDC_HOSTED__=1");
            list.add("_LARGEFILE64_SOURCE=1");
            list.add("__LDBL_HAS_INFINITY__=1");
            list.add("__DBL_DIG__=15");
            list.add("__FLT_EPSILON__=1.19209290e-7F");
            list.add("__GXX_WEAK__=1");
            list.add("__LDBL_MIN__=3.36210314311209350626e-4932L");
            list.add("__unix__=1");
            list.add("__DECIMAL_DIG__=21");
            list.add("_LARGEFILE_SOURCE=1");
            list.add("__LDBL_HAS_QUIET_NAN__=1");
            list.add("__GNUC__=3");
            list.add("__DBL_MAX__=1.7976931348623157e+308");
            list.add("__DBL_HAS_INFINITY__=1");
            list.add("__SVR4=1");
            list.add("__cplusplus=1");
            list.add("__DEPRECATED=1");
            list.add("__DBL_MAX_EXP__=1024");
            list.add("__GNUG__=3");
            list.add("__LONG_LONG_MAX__=9223372036854775807LL");
            list.add("__GXX_ABI_VERSION=1002");
            list.add("__FLT_MIN_EXP__=(-125)");
            list.add("__DBL_MIN__=2.2250738585072014e-308");
            list.add("__FLT_MIN_10_EXP__=(-37)");
            list.add("__DBL_HAS_QUIET_NAN__=1");
            list.add("__tune_i386__=1");
            list.add("__REGISTER_PREFIX__");
            list.add("__NO_INLINE__=1");
            list.add("__i386=1");
            list.add("__FLT_MANT_DIG__=24");
            list.add("__VERSION__=\"3.4.3 (csl-sol210-3_4-branch+sol_rpath)\"");
            list.add("i386=1");
            list.add("unix=1");
            list.add("__i386__=1");
            list.add("__SIZE_TYPE__=unsigned int");
            list.add("__ELF__=1");
            list.add("__FLT_RADIX__=2");
            list.add("__LDBL_EPSILON__=1.08420217248550443401e-19L");
            list.add("__FLT_HAS_QUIET_NAN__=1");
            list.add("__FLT_MAX_10_EXP__=38");
            list.add("__LONG_MAX__=2147483647L");
            list.add("__FLT_HAS_INFINITY__=1");
            list.add("__PRAGMA_REDEFINE_EXTNAME=1");
            list.add("__EXCEPTIONS=1");
            list.add("__LDBL_MANT_DIG__=64");
            list.add("__WCHAR_TYPE__=long int");
            list.add("__FLT_DIG__=6");
            list.add("__INT_MAX__=2147483647");
            list.add("__FLT_MAX_EXP__=128");
            list.add("__DBL_MANT_DIG__=53");
            list.add("__WINT_TYPE__=long int");
            list.add("__LDBL_MIN_EXP__=(-16381)");
            list.add("__LDBL_MAX_10_EXP__=4932");
            list.add("__DBL_EPSILON__=2.2204460492503131e-16");
            list.add("__svr4__=1");
            list.add("__FLT_DENORM_MIN__=1.40129846e-45F");
            list.add("__FLT_MAX__=3.40282347e+38F");
            list.add("__GNUC_MINOR__=4");
            list.add("__DBL_MAX_10_EXP__=308");
            list.add("__LDBL_DENORM_MIN__=3.64519953188247460253e-4951L");
            list.add("__PTRDIFF_TYPE__=int");
            list.add("__LDBL_MIN_10_EXP__=(-4931)");
            list.add("__LDBL_DIG__=18");
        }
	return list;
    }
    
    public List getSystemIncludeDirectories(Platform platform) {
        // FIXUP: add actual include directories
        // FIXUP: use 'platform' to return different list on different platforms.
        Vector list = new Vector();
        if (platform.getId() == Platform.PLATFORM_SOLARIS_INTEL || platform.getId() == Platform.PLATFORM_SOLARIS_SPARC) {
            if( fullIncludes ) {
                list.add("/usr/sfw/include/c++/3.4.3"); // NOI18N
                list.add("/usr/sfw/include/c++/3.4.3/i386-pc-solaris2.10"); // NOI18N
                list.add("/usr/sfw/include/c++/3.4.3/backward"); // NOI18N
                list.add("/usr/local/include"); // NOI18N
                list.add("/usr/sfw/include"); // NOI18N
                list.add("/usr/sfw/lib/gcc/i386-pc-solaris2.10/3.4.3/include"); // NOI18N
            }
            list.add("/usr/include"); // NOI18N
        }
        else if (platform.getId() == Platform.PLATFORM_LINUX) {
            if( fullIncludes ) {
                list.add("/usr/include"); // NOI18N
                list.add("/usr/include/linux"); // NOI18N
                list.add("/usr/include/gnu"); // NOI18N
                list.add("/usr/include/g++"); // NOI18N
            }
            list.add("/usr/include/g++/backward"); // NOI18N
        }
        else if (platform.getId() == Platform.PLATFORM_WINDOWS) {
            //list.add("???"); // NOI18N
        }
	return list;
    }
}
