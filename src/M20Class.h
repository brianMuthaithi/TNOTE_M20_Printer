/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class M20Class */

#ifndef _Included_M20Class
#define _Included_M20Class
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     M20Class
 * Method:    CPSC1900Connect
 * Signature: (BLjava/lang/String;)B
 */
JNIEXPORT jbyte JNICALL Java_M20Class_CPSC1900Connect
  (JNIEnv *, jobject, jbyte, jstring);

/*
 * Class:     M20Class
 * Method:    CPSC1900EmbosserCleanLineData
 * Signature: ()B
 */
JNIEXPORT jbyte JNICALL Java_M20Class_CPSC1900EmbosserCleanLineData
  (JNIEnv *, jobject);

/*
 * Class:     M20Class
 * Method:    CPSC1900EmbosserDownloadLineData
 * Signature: (Ljava/lang/String;II)B
 */
JNIEXPORT jbyte JNICALL Java_M20Class_CPSC1900EmbosserDownloadLineData
  (JNIEnv *, jobject, jstring, jint, jint);

/*
 * Class:     M20Class
 * Method:    CPSC1900FeedCard
 * Signature: ()B
 */
JNIEXPORT jbyte JNICALL Java_M20Class_CPSC1900FeedCard
  (JNIEnv *, jobject);

/*
 * Class:     M20Class
 * Method:    CPSC1900EmbosserEmbossLines
 * Signature: (Z)B
 */
JNIEXPORT jbyte JNICALL Java_M20Class_CPSC1900EmbosserEmbossLines
  (JNIEnv *, jobject, jboolean);

/*
 * Class:     M20Class
 * Method:    CPSC1900TopperPressCard
 * Signature: (ZB)B
 */
JNIEXPORT jbyte JNICALL Java_M20Class_CPSC1900TopperPressCard
  (JNIEnv *, jobject, jboolean, jbyte);

/*
 * Class:     M20Class
 * Method:    CPSC1900EjectCard
 * Signature: (B)B
 */
JNIEXPORT jbyte JNICALL Java_M20Class_CPSC1900EjectCard
  (JNIEnv *, jobject, jbyte);

/*
 * Class:     M20Class
 * Method:    CPSC1900Disconnect
 * Signature: ()B
 */
JNIEXPORT jbyte JNICALL Java_M20Class_CPSC1900Disconnect
  (JNIEnv *, jobject);

/*
 * Class:     M20Class
 * Method:    CPSC1900DeviceReset
 * Signature: ()B
 */
JNIEXPORT jbyte JNICALL Java_M20Class_CPSC1900DeviceReset
  (JNIEnv *, jobject);

#ifdef __cplusplus
}
#endif
#endif
