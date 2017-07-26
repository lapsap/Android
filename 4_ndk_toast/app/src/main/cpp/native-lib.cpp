#include <jni.h>
#include <string>

extern "C"
JNIEXPORT void JNICALL
Java_net_nctucs_lapsap_a4_1ndk_1toast_MainActivity_calltoast(JNIEnv *env, jobject object_main, jstring input) {

    jclass ref_context = env->FindClass("android/content/Context");
    jmethodID  method_context = env->GetMethodID(ref_context, "getApplicationContext", "()Landroid/content/Context;");
    jobject  object_context = env->CallObjectMethod(object_main, method_context);

    jclass ref_toast = env->FindClass("android/widget/Toast");
    jmethodID method_toast = env->GetStaticMethodID(ref_toast, "makeText", "(Landroid/content/Context;Ljava/lang/CharSequence;I)Landroid/widget/Toast;");
    jobject  object_toast = env->CallStaticObjectMethod(ref_toast, method_toast, object_context, input , 3500);
    jmethodID  method_toast_show = env->GetMethodID(ref_toast, "show", "()V");
    env->CallVoidMethod(object_toast, method_toast_show );

}
