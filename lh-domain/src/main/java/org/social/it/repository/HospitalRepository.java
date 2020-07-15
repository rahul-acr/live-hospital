package org.social.it.repository;

import org.bson.types.ObjectId;
import org.social.it.entity.HospitalEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HospitalRepository extends MongoRepository<HospitalEntity, ObjectId> {
}
