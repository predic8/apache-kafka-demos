# Vorbereitung

## Sender

props.put(BATCH_SIZE_CONFIG, 0);
props.put(LINGER_MS_CONFIG, 0);


1. Start Consumer
2. Start Sender
3. Zeit ansehen
4. Werte im Sender ändern:

props.put(BATCH_SIZE_CONFIG, 32000);
props.put(LINGER_MS_CONFIG, 100);

5. Consumer starten
6. Sender Starten
7. Werte ansehen