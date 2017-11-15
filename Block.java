
public class Block {

	private long blockNo;
	private double blockRandNum;
	private String blockData;
	private String blockHashkey;
	private String prevBlockHashKey;

	public Block(double randNum, String data, String hashKey) {

		// IF NOT FIRST BLOCK THEN ASSIGN A NUMBER
		if (Main.pendingPool.size() != 0) {
			this.blockNo = Main.pendingPool.get(Main.pendingPool.size() - 1).getBlockNo() + 1;
		}

		else {
			this.blockNo = 0;
		}
		this.blockRandNum = randNum;
		this.blockData = data;
		this.blockHashkey = hashKey;

		// IF NOT FIRST BLOCK THEN GET THE HASHKEY OF THE PREVIOUS BLOCK
		if (Main.pendingPool.size() != 0) {
			this.prevBlockHashKey = Main.pendingPool.get(Main.pendingPool.size() - 1).getBlockHashkey();
		} else {
			this.prevBlockHashKey = "0";
		}

	}

	public long getBlockNo() {
		return blockNo;
	}

	public void setBlockNo(long blockNo) {
		this.blockNo = blockNo;
	}

	public double getBlockRandNum() {
		return blockRandNum;
	}

	public void setBlockRandNum(double randNum) {
		this.blockRandNum = randNum;
	}

	public String getBlockData() {
		return blockData;
	}

	public void setBlockData(String blockData) {
		this.blockData = blockData;
	}

	public String getBlockHashkey() {
		return blockHashkey;
	}

	public void setBlockHashkey(String blockHashkey) {
		this.blockHashkey = blockHashkey;
	}

	public String getPrevBlockHashKey() {
		return prevBlockHashKey;
	}

	public void setPrevBlockHashKey(String prevBlockHashKey) {
		this.prevBlockHashKey = prevBlockHashKey;
	}

	@Override
	public String toString() {
		return "Block no:" + blockNo + ", Block Data:" + "{" + blockData + "}, Hashkey:" + blockHashkey
				+ ", Previous Hashkey:" + prevBlockHashKey;
	}
}
