package com.github.mizo432.xplanner.buisiness.model.repository;

import com.github.mizo432.xplanner.buisiness.model.Nameable;

@Deprecated
public interface ObjectRepository {
    /**
     * Delete an object using it's object ID (OID).
     * @param objectIdentifier
     */
    void delete(Long objectIdentifier) throws RepositoryException;

    /**
     * Load an instance of an object
     * @param objectIdentifier
     */
    Object load(Long objectIdentifier) throws RepositoryException;

    /**
     * Create a new instance in the repository
     * @param object the object to insert
     * @return the object identifier
     */
    Long insert(Nameable object) throws RepositoryException;

    /**
     * Updates an object in the repository. Note: This is a no-op for Hibernate.
     * @param object - the object to update
     */
    void update(Nameable object) throws RepositoryException;
}
