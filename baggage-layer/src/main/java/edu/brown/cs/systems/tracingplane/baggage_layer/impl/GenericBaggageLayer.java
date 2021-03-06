package edu.brown.cs.systems.tracingplane.baggage_layer.impl;

import edu.brown.cs.systems.tracingplane.baggage_layer.BaggageLayer;
import edu.brown.cs.systems.tracingplane.baggage_layer.protocol.BaggageReader;
import edu.brown.cs.systems.tracingplane.baggage_layer.protocol.BaggageWriter;
import edu.brown.cs.systems.tracingplane.transit_layer.Baggage;

/**
 * <p>
 * GenericBaggageLayer is the default baggage layer implementation when baggage buffers is <b>not</b> used. It is a
 * better idea to use BaggageBuffers, because the GenericBaggageLayer creates lists and maps of baggage contents,
 * whereas BaggageBuffers is smarter and provides a <b>much</b> cleaner interface.
 * </p>
 * 
 * <p>
 * The logic for branch, join, parse and serialize is in {@link GenericBaggageContentsHandler}, in keeping with the
 * style for baggagebuffers generated code.
 * </p>
 * 
 * <p>
 * Because this is a clunky implementation, there is no API for accessing or manipulating the contents of the baggage
 * beyond checking if it's an instance of {@link GenericBaggageContents} then casting and accessing the data directly.
 * </p>
 */
public class GenericBaggageLayer implements BaggageLayer<GenericBaggageContents> {

    @Override
    public boolean isInstance(Baggage baggage) {
        return baggage == null || baggage instanceof GenericBaggageContents;
    }

    @Override
    public GenericBaggageContents newInstance() {
        return null;
    }

    @Override
    public void discard(GenericBaggageContents baggage) {}

    @Override
    public GenericBaggageContents branch(GenericBaggageContents from) {
        return from == null ? null : from.branch();
    }

    @Override
    public GenericBaggageContents join(GenericBaggageContents left, GenericBaggageContents right) {
        if (left == null && right == null) {
            return null;
        } else if (left == null) {
            return right;
        } else if (right == null) {
            return left;
        } else {
            return left.mergeWith(right);
        }
    }

    @Override
    public GenericBaggageContents read(BaggageReader reader) {
        return GenericBaggageContents.parseFrom(reader);
    }

    @Override
    public BaggageWriter write(GenericBaggageContents instance) {
        if (instance == null) {
            return null;
        }
        BaggageWriter writer = BaggageWriter.create();
        instance.serializeTo(writer);
        return writer;
    }

}
