package tdd.collection.lifo;

import tdd.collection.Structure;

import java.util.List;

public interface SumpStack extends Structure {

    void pushBatch(List<Object> batch);

    List<Object> pullBatch();
}
