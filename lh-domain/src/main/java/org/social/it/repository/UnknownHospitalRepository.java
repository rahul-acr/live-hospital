package org.social.it.repository;

import org.bson.types.ObjectId;
import org.social.it.entity.UnknownHospitalEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnknownHospitalRepository extends MongoRepository<UnknownHospitalEntity, ObjectId> {
}
