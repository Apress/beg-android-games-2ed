LOCAL_PATH:= $(call my-dir)
include $(CLEAR_VARS)
 
LOCAL_MODULE    := jniutils
LOCAL_LDLIBS := -lm -llog
LOCAL_ARM_MODE  := arm
LOCAL_SRC_FILES := jniutils.cpp
 
include $(BUILD_SHARED_LIBRARY)
