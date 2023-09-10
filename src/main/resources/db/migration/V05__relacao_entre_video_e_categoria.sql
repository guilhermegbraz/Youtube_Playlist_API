ALTER TABLE video
ADD categoria_id BIGINT;

UPDATE video SET categoria_id = 1 where categoria_id IS NULL;


ALTER TABLE video
    ADD CONSTRAINT fk_categoria_id FOREIGN KEY (categoria_id) REFERENCES categoria (id);

