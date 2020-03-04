package org.pp.commons.apache.crypto;

//import com.sun.jna.Library;
//import com.sun.jna.Native;
//import com.sun.jna.Platform;
//import com.sun.jna.win32.StdCallLibrary;

public class JNADllCall {
//
//    public interface StdCallDll extends StdCallLibrary {
//        // DLL文件默认路径为项目根目录，若DLL文件存放在项目外，请使用绝对路径
//        StdCallDll INSTANCE = (StdCallDll) Native.loadLibrary((Platform.isWindows() ? "msvcrt" : "c"),
//                StdCallDll.class);// 加载动态库文件
//        // 声明将要调用的DLL中的方法（可以是多个方法）
//
//        void printf(String format, Object... args);
//    }
//
//    public interface CLibrary extends Library {
//        // DLL文件默认路径为项目根目录，若DLL文件存放在项目外，请使用绝对路径。（此处：(Platform.isWindows()?"msvcrt":"c")指本地动态库msvcrt.dll）
//        CLibrary INSTANCE = (CLibrary) Native.loadLibrary((Platform.isWindows() ? "msvcrt" : "c"),
//                CLibrary.class);
//
//        // 声明将要调用的DLL中的方法,可以是多个方法(此处示例调用本地动态库msvcrt.dll中的printf()方法)
//        void printf(String format, Object... args);
//    }
//
//    public static void main(String[] args) {
//        StdCallDll.INSTANCE.printf("Hello, World!\n");
//        CLibrary.INSTANCE.printf("Hello, World!");
//    }
}
