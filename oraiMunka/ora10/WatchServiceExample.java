import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

public class WatchServiceExample {
	public static void main(String[] args) throws Exception {
		String dirname = "src/watched";
		watcher(dirname);
	}
	
	private static void watcher(String dirname) throws IOException, InterruptedException {
		Path dir = Paths.get(dirname);
		WatchService watch = dir.getFileSystem().newWatchService();
		dir.register(watch, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY);

		System.out.println("Started watching");
		
		while (true) {
			WatchKey key = watch.take();

			for (WatchEvent<?> event : key.pollEvents()) {
				Kind<?> kind = event.kind();
				Path path = (Path)event.context();

				if (kind == StandardWatchEventKinds.ENTRY_CREATE)   System.out.println("CREATE");
				if (kind == StandardWatchEventKinds.ENTRY_DELETE)   System.out.println("DELETE");
				if (kind == StandardWatchEventKinds.ENTRY_MODIFY)   System.out.println("MODIFY");
				
				System.out.println("Event " + event + " " + kind + " " + path);
			}
			
			key.reset();
			
			System.out.println("next...");
		}
	}
}
