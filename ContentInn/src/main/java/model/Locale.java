package main.java.model;

public enum Locale {
	en, uk, ru, pl, cz, hy, lv, lt, tr, el, by, ge, hu, ar, ro, sr, bg, en_IL, az, fr;
		
	public static Locale find(String name) {
		Locale fl = null;
		for (Locale l : Locale.values()) {
			if (l.name().equals(name)) {
				fl = l;
				break;
			}
			else continue;
		}
		return fl;
	}
}
