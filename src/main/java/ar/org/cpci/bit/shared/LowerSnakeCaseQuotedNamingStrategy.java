package ar.org.cpci.bit.shared;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;
import org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy;

public class LowerSnakeCaseQuotedNamingStrategy extends SpringPhysicalNamingStrategy {

    @Override
    protected Identifier getIdentifier(String name, boolean quoted, JdbcEnvironment env) {
        // Always between quotation marks, for compatibility reasons
        return new Identifier(name, true);
    }

}
