module jwhisper.common {
    requires static lombok;

    requires com.fasterxml.jackson.annotation;
    requires org.bouncycastle.provider;
    requires tools.jackson.core;
    requires tools.jackson.databind;
    requires org.slf4j;

    exports io.github.artshp.jwhisper.common.crypto;
    exports io.github.artshp.jwhisper.common.exception;
    exports io.github.artshp.jwhisper.common.io;
    exports io.github.artshp.jwhisper.common.protocol;
}
