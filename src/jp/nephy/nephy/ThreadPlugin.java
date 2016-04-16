package jp.nephy.nephy;

import java.io.File;

class ThreadPlugin extends Thread {
	public ThreadPlugin() {
	}

	public void run() {
		//実際の処理
	}

	public static void main() {
		System.out.println('a');
		String path = "./plugins";
	    File dir = new File(path);
	    String[] files = dir.list();
	    for (int i = 0; i < files.length; i++) {
	        String file = files[i];
	    }
	}
}