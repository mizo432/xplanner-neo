package com.github.mizo432.xplanner.buisiness.model;

public enum IterationStatus {
	ACTIVE(Short.valueOf("0")), INACTIVE(Short.valueOf("1"));
	protected final short code;

    public static final String ACTIVE_KEY = "active";
    public static final String INACTIVE_KEY = "inactive";

    static final String[] KEYS = {ACTIVE_KEY, INACTIVE_KEY};

    private IterationStatus(short code){
        this.code = code;
    }

    public String getKey (){
        return KEYS[code];
    }

    public short toInt() {
       return code;
    }


    public static IterationStatus fromKey(String key) {
       if (key == null) {
          return INACTIVE;
       } else if (ACTIVE_KEY.equals(key)) {
          return ACTIVE;
       } else if (INACTIVE_KEY.equals(key)) {
          return INACTIVE;
       } else {
          throw new RuntimeException("Unknown iteration status key");
       }
    }

	   public String toString() {
	      return getKey();
	   }

	public static IterationStatus fromInt(Short statusShort) {
		for (IterationStatus iterationStatus : values()) {
			if(iterationStatus.code == statusShort){
				return iterationStatus;
			}
		}
		return INACTIVE;
	}

}
