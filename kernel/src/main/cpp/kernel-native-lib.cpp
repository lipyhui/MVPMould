#include <jni.h>
#include <string>

extern "C"
JNIEXPORT jstring JNICALL
Java_com_kawakp_kp_kernel_KernelJNI_stringFromJNI(
		JNIEnv *env,
		jobject /* this */) {
	std::string hello = "This is JNI test!";
	return env->NewStringUTF(hello.c_str());
}
