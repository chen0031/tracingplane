package edu.brown.cs.systems.tracingplane.context_layer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import edu.brown.cs.systems.tracingplane.transit_layer.Baggage;
import edu.brown.cs.systems.tracingplane.transit_layer.TransitLayer;

public class TransitLayerImpl<T extends BaggageAtoms> implements TransitLayer<T> {

	public final ContextLayer<T> contextLayer;

	public TransitLayerImpl(ContextLayer<T> contextLayer) {
		this.contextLayer = contextLayer;
	}

	@Override
	public boolean isInstance(Baggage baggage) {
		if (baggage == null || baggage instanceof BaggageAtoms) {
			return contextLayer.isInstance((BaggageAtoms) baggage);
		} else {
			return false;
		}
	}

	@Override
	public T newInstance() {
		return contextLayer.newInstance();
	}

	@Override
	public void discard(T baggage) {
		contextLayer.discard(baggage);
	}

	@Override
	public T branch(T from) {
		return contextLayer.branch(from);
	}

	@Override
	public T join(T left, T right) {
		return contextLayer.join(left, right);
	}

	@Override
	public T deserialize(byte[] serialized, int offset, int length) {
		return contextLayer.wrap(ContextLayerSerialization.deserialize(serialized, offset, length));
	}

	@Override
	public T readFrom(InputStream in) throws IOException {
		return contextLayer.wrap(ContextLayerSerialization.readFrom(in));
	}

	@Override
	public byte[] serialize(T baggage) {
		return ContextLayerSerialization.serialize(contextLayer.atoms(baggage));
	}

	@Override
	public void writeTo(OutputStream out, T baggage) throws IOException {
		ContextLayerSerialization.write(out, contextLayer.atoms(baggage));
	}

}
