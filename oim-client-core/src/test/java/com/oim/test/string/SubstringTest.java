package com.oim.test.string;

/**
 * @author XiaHui
 * @date 2017-11-10 10:47:20
 */
public class SubstringTest {

	public static void main(String[] args) {
		String path = "file:";
		int l = path.length();
		String url = (l >= 5) ? path.substring(5, l) : path;
		System.out.println(url);

		String m = "1.2.2001";

		//int length = m.length();
		int index = m.indexOf(".2.");
		System.out.println(index);
//		if (-1 != index && length > index) {
//			String type = m.substring(index, index + 1);
//			System.out.println(type);
//		}
	}
}
