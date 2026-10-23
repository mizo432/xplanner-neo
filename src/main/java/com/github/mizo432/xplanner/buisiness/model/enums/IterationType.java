package com.github.mizo432.xplanner.buisiness.model.enums;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

public enum IterationType {
	REGULAR(0), BACKLOG(1);
	private static final List<IterationType> ALL_VALUES = Collections.unmodifiableList(Arrays.asList(values()));
	private int id;
	
	private IterationType(int id){
		this.id = id;
	}
	
	public int getCode(){
		return id;
	}
	
	public String getMessageCode(){
		return "iteration.type." + toString().toLowerCase();
	}
	
	@SuppressFBWarnings(value = "MS_EXPOSE_REP", justification = "Returns unmodifiable list, safe for this use case")
	public static List<IterationType> getAllValues(){
		return ALL_VALUES;
	}
}
