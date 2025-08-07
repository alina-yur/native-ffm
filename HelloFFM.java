import java.lang.foreign.*;
import java.lang.invoke.MethodHandle;

public class HelloFFM {
    public static void main(String[] args) throws Throwable {
        Linker linker = Linker.nativeLinker();
        MemorySegment strlen = linker.defaultLookup().find("strlen").orElseThrow();
        MethodHandle handle = linker.downcallHandle(strlen, 
            FunctionDescriptor.of(ValueLayout.JAVA_LONG, ValueLayout.ADDRESS));
        
        Arena arena = Arena.ofShared();
        MemorySegment str = arena.allocateFrom("Hello FFM!");
        long length = (long) handle.invoke(str);
        System.out.println(length);
    }
}
