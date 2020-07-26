package org.social.it.domain;

import org.bson.types.ObjectId;

public interface Hospital extends Identifiable<ObjectId> {

    String name();

    String additionalInfo();

    Location location();

    UsageStatistics usage();

    boolean isPrivate();

}
