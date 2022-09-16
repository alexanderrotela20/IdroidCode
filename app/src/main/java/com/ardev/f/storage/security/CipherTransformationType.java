package com.ardev.f.storage.security;
        
        
        public class CipherTransformationType {
        	private static final String separator = "/";
        
        	public static final String AES_CBC_NoPadding = CipherAlgorithmType.AES + separator + CipherModeType.CBC + separator + CipherPaddingType.NoPadding;
        	public static final String AES_CBC_PKCS5Padding = CipherAlgorithmType.AES + separator + CipherModeType.CBC + separator + CipherPaddingType.PKCS5Padding;
        	public static final String AES_ECB_NoPadding = CipherAlgorithmType.AES + separator + CipherModeType.ECB + separator + CipherPaddingType.NoPadding;
        	public static final String AES_ECB_PKCS5Padding = CipherAlgorithmType.AES + separator + CipherModeType.ECB + separator + CipherPaddingType.PKCS5Padding;
        
        	public static final String DES_CBC_NoPadding = CipherAlgorithmType.DES + separator + CipherModeType.CBC + separator + CipherPaddingType.NoPadding;
        	public static final String DES_CBC_PKCS5Padding = CipherAlgorithmType.DES + separator + CipherModeType.CBC + separator + CipherPaddingType.PKCS5Padding;
        	public static final String DES_ECB_NoPadding = CipherAlgorithmType.DES + separator + CipherModeType.ECB + separator + CipherPaddingType.NoPadding;
        	public static final String DES_ECB_PKCS5Padding = CipherAlgorithmType.DES + separator + CipherModeType.ECB + separator + CipherPaddingType.PKCS5Padding;
        
        	public static final String DESede_CBC_NoPadding = CipherAlgorithmType.DESede + separator + CipherModeType.CBC + separator + CipherPaddingType.NoPadding;
        	public static final String DESede_CBC_PKCS5Padding = CipherAlgorithmType.DESede + separator + CipherModeType.CBC + separator + CipherPaddingType.PKCS5Padding;
        	public static final String DESede_ECB_NoPadding = CipherAlgorithmType.DESede + separator + CipherModeType.ECB + separator + CipherPaddingType.NoPadding;
        	public static final String DESede_ECB_PKCS5Padding = CipherAlgorithmType.DESede + separator + CipherModeType.ECB + separator + CipherPaddingType.PKCS5Padding;
        
        	public static final String RSA_ECB_PKCS1Padding = CipherAlgorithmType.RSA + separator + CipherModeType.ECB + separator + CipherPaddingType.PKCS1Padding;
        	public static final String RSA_ECB_OAEPWithSHA_1AndMGF1Padding = CipherAlgorithmType.RSA + separator + CipherModeType.ECB + separator + CipherPaddingType.OAEPWithSHA_1AndMGF1Padding;
        	public static final String RSA_ECB_OAEPWithSHA_256AndMGF1Padding = CipherAlgorithmType.RSA + separator + CipherModeType.ECB + separator + CipherPaddingType.OAEPWithSHA_256AndMGF1Padding;
        
        }
        