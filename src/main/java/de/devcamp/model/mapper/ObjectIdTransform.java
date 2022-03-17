package de.devcamp.model.mapper;

import org.bson.types.ObjectId;

public class ObjectIdTransform {
    public String objectIdToString(ObjectId id) {
        return id.toString();
    }

    public ObjectId strToObjectId(String str) {
        if (str == null || str.isEmpty())
            return null;
        else
            return new ObjectId(str);
    }
}