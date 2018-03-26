
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;

public class Peer extends Thread {

	private ArrayList<Block> ledger = new ArrayList<Block>();
	private int ip;
	private int difficulty = 25;

	public Peer(int ip) {
		this.ip = ip;

	}

	public void run() {
		System.out.println("Peer:" + ip + " is connected");
		while (true) {
			try {
				Main.mutex.acquire();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// IF THERE IS A BLOCK TO MINE THEN MINE IT.
			if (Main.pendingBlock != null) {

				if (ip == 1) {
					mineblock();
				}
			}
			Main.mutex.release();

			try {
				Thread.sleep(1000);
			} 
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	public void addBlock() {
		double randNum = (int) (Math.random() * difficulty); // HASKEY STUFF
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-256");
		} 
		catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		byte[] digest = md.digest(Double.toString(randNum).getBytes()); // Missing charset
		String hashkey = Base64.getEncoder().encodeToString(digest); // HASKEY STUFF END

		Block newblock = new Block(randNum, "data, ip:" + ip, hashkey);

		// IF PENDING BLOCKS EXIST MAKE SURE PREVIOUS HASKEY IS EQUAL TO THE NEW
		// "PREVIOUS" HASHKEY
		if (Main.pendingPool.size() > 0) {
			if (Main.pendingPool.get(Main.pendingPool.size() - 1).getBlockHashkey()
					.equals(newblock.getPrevBlockHashKey())) {
				Main.pendingPool.add(newblock);
			}
		}

		else {
			Main.pendingPool.add(newblock);
		}

	}

	public boolean mineblock() {
		if (Main.pendingBlock != null) {
			MessageDigest md = null;
			try {
				md = MessageDigest.getInstance("SHA-256");
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			double randNum = (int) (Math.random() * difficulty);
			byte[] digest = md.digest(Double.toString(randNum).getBytes()); // Missing charset
			String hashkey = Base64.getEncoder().encodeToString(digest);

			if (hashkey.substring(0, 2).equals(Main.pendingBlock.getBlockHashkey().substring(0, 2))) {
				for (int i = 0; i < Main.peers.size(); i++) {
					Main.peers.get(i).getLedger().add(Main.pendingBlock);
					System.out.println("Peer:" + i + " received a new block:" + Main.pendingBlock.toString());
				}
				// ADD MINED BLOCK TO MAIN LEDGER
				Main.ledger.add(Main.pendingBlock);

				// SET PENDING BLOCKS TO NULL
				Main.pendingBlock = null;
				return true;
			}
		}

		return false;
	}

	public ArrayList<Block> getLedger() {
		return ledger;
	}

	public void setLedger(ArrayList<Block> ledger) {
		this.ledger = ledger;
	}

}
