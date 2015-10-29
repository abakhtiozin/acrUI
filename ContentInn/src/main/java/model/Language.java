package main.java.model;

public enum Language {
	English		(Locale.en),
	Ukrainian	(Locale.uk),
	Russian		(Locale.ru),
	Poland		(Locale.pl),
	Armenian	(Locale.hy),
	Latvian		(Locale.lv),
	Lithuanian	(Locale.lt),
	Turkish		(Locale.tr),
	Greek		(Locale.el),
	Belorussian	(Locale.by),
	Czech		(Locale.cz),
	Georgian	(Locale.ge),
	Hungarian	(Locale.hu),
	Arabic		(Locale.ar),
	Romanian	(Locale.ro),
	Serbian		(Locale.sr),
	Bulgarian	(Locale.bg),
	English_IL	(Locale.en_IL),
	Azerbaijan	(Locale.az),
	French		(Locale.fr);
	
	private final Locale locale;
	
	Language (Locale locale) { 
		this.locale = locale;
	}

	public Locale locale()	{ return this.locale; }
	
	public static Language findByCulture(Locale locale) {
		Language fl = null;
		for (Language l : Language.values()) {
			if (l.locale.equals(locale)) {
				fl = l;
				break;
			}
			else continue;
		}
		return fl;
	}
		
}
