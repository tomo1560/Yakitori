package jp.nephy.test;

public class SampleInheritanceChildren extends SampleInheritanceParent{
}

class SampleInheritanceMain {
	public static void main(String[] args) {
		SampleInheritanceChildren a = new SampleInheritanceChildren();
		
		//親クラス:メンバにアクセス
		System.out.println(a.inmu);
		a.ｱｰｲｷｿ();
	}
}