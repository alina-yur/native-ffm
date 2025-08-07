/*modified from https://dev.java/learn/ffm/native/ */

import java.lang.foreign.*;
import java.lang.invoke.MethodHandle;
import static java.lang.foreign.ValueLayout.JAVA_BYTE;

public class HelloFFM {
    public static void main(String[] args) throws Throwable {
        String pattern = (args.length > 0) ? args[0] : "********";

        Linker linker = Linker.nativeLinker();
        SymbolLookup stdLib = linker.defaultLookup();
        MemorySegment strdup_addr = stdLib.find("strdup").get();

        var layout = MemoryLayout.sequenceLayout(Long.MAX_VALUE, JAVA_BYTE);
        FunctionDescriptor strdup_sig = FunctionDescriptor.of(
                ValueLayout.ADDRESS.withTargetLayout(layout),
                ValueLayout.ADDRESS.withTargetLayout(layout));

        MethodHandle strdup_handle = linker.downcallHandle(strdup_addr, strdup_sig);

        Arena arena = Arena.ofShared();
        MemorySegment nativeString = arena.allocateFrom(pattern);
        MemorySegment duplicatedAddress = (MemorySegment) strdup_handle.invokeExact(nativeString);
        String duplicated = duplicatedAddress.getString(0);

        for (int i = 0; i < pattern.length(); i++) {
            System.out.println(duplicated.substring(0, i + 1));
        }
    }
}