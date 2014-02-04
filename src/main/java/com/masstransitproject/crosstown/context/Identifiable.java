package com.masstransitproject.crosstown.context;

import java.util.UUID;

/**
 * Tag interface that exposes the getId method so message creators do not need to make an extra call
 * to pass an Id into SendContext constructor.
 * 
 */
public interface Identifiable {

	public UUID getId();

	public void setId(UUID id);
}
