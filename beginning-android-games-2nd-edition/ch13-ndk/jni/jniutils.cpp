#include <android/log.h>
#include <string.h>
#include "jniutils.h"

JNIEXPORT void JNICALL Java_com_badlogic_androidgames_framework_JniUtils_log
  (JNIEnv *env, jclass clazz, jstring tag, jstring message) {
	const char *cTag = env->GetStringUTFChars(tag, 0);
	const char *cMessage = env->GetStringUTFChars(message, 0);

	__android_log_print(ANDROID_LOG_VERBOSE, cTag, cMessage);

	env->ReleaseStringUTFChars(tag, cTag);
	env->ReleaseStringUTFChars(message, cMessage);
}

JNIEXPORT void JNICALL Java_com_badlogic_androidgames_framework_JniUtils_copy
  (JNIEnv *env, jclass clazz, jobject dst, jfloatArray src, jint offset, jint len) {
    unsigned char* pDst = (unsigned char*)env->GetDirectBufferAddress(dst);
    float* pSrc = (float*)env->GetPrimitiveArrayCritical(src, 0);
    memcpy(pDst, pSrc + offset, len * 4);
    env->ReleasePrimitiveArrayCritical(src, pSrc, 0);
}
