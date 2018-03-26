
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Main {

	public static Semaphore mutex = new Semaphore(1);

	public static ArrayList<Block> ledger = new ArrayList<Block>();

	public static Block pendingBlock;

	public static ArrayList<Block> pendingPool = new ArrayList<Block>();

	public static ArrayList<Peer> peers = new ArrayList<Peer>();

	public static void main(String args[]) throws InterruptedException {
		
		for(int i = 0; i < 5; i++) {
			peers.add(new Peer(i));
			peers.get(i).start();
			
		}
		int count = 0;
		while(true) {
			// IF PENDING BLOCKS WAITING ASSIGN TO SOLVE
			if(pendingBlock == null && pendingPool.size() > 0) {
				pendingBlock = pendingPool.get(count);
				count ++;
				
			}
			//ADD MORE BLOCKS
			int randomNum = 0 + (int)(Math.random() * 4); 
			Main.mutex.acquire();
			peers.get(randomNum).addBlock();
			Main.mutex.release();
			Thread.sleep(1000);
		}	
	}

}
