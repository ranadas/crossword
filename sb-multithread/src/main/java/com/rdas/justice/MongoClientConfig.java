package com.rdas.justice;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.rdas.justice.model.Results;
import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistries;
import static org.bson.codecs.configuration.CodecRegistries.fromCodecs;

@Profile("justice")
@Configuration
public class MongoClientConfig {
    static class ActorCodec implements Codec<Results> {

        private final Codec<Document> documentCodec;

        public ActorCodec(Codec<Document> documentCodec) {
            this.documentCodec = documentCodec;
        }

        @Override
        public void encode(BsonWriter writer, Results value, EncoderContext encoderContext) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public Class<Results> getEncoderClass() {
            return Results.class;
        }

        @Override
        public Results decode(BsonReader reader, DecoderContext decoderContext) {
            Document document = documentCodec.decode(reader, decoderContext);
            Results results = new Results();
            results.setTitle(document.getString("title"));
            results.setBody(document.getString("body"));
            return results;
            //return new Actor(document.getString("firstName"), document.getString("lastName"));
        }
    }

    @Bean
    public MongoClientOptions mongoClientOption() {
        final CodecRegistry defaultCodecRegistry = MongoClient.getDefaultCodecRegistry();
        final Codec<Document> defaultDocumentCodec = defaultCodecRegistry.get(Document.class);

        final CodecRegistry codecRegistry =
                CodecRegistries.fromRegistries(
                        defaultCodecRegistry,
                        fromCodecs(new ActorCodec(defaultDocumentCodec))
                );

        return MongoClientOptions
                .builder()
                .codecRegistry(codecRegistry)
                .build();
    }
}
