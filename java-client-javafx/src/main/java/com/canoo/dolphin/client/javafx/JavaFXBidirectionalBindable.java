package com.canoo.dolphin.client.javafx;

import com.canoo.dolphin.event.Subscription;
import com.canoo.dolphin.mapping.Property;

/**
 * Created by hendrikebbers on 27.09.15.
 */
public interface JavaFXBidirectionalBindable<S> extends JavaFXBindable<S> {

    default Subscription bidirectionalTo(Property<S> dolphinProperty) {
        return bidirectionalTo(dolphinProperty, new BidirectionalConverter<S, S>() {
            @Override
            public S convertBack(S value) {
                return value;
            }

            @Override
            public S convert(S value) {
                return value;
            }
        });
    }

    <T> Subscription bidirectionalTo(final Property<T> property, BidirectionalConverter<T, S> converter);

}
