# Log Retention und Bereinigung

Achtung: Geht nur mit grossen Datenmengen. Bei wenigen Megabytes wird nicht bereinigt.

# Vorbereitung:

1. ./setup-compacht

node-0.properties:

log.retention.ms=20000
log.retention.check.interval.ms=1000

# Durchführung

1. ./setup-delete.sh
1. log/node-0/produktion/* zeigen => 0.log => Keine Nachrichten
1. exec SimpleProducer
1. log/node-0/produktion/* zeigen => viele Dateien
1. exec SimpleConsumer => Alle Nachrichten
1. exec SimpleConsumer => Alle Nachrichten


# Compacting

1. ./setup-compact.sh
1. exec SimpleProducer
1. Verzeichnis logs/node-0/bar-0 ansehen
1. exec RetentionCompactConsumer
1. Anzahl gelesene Nachrichten ansehen
1. Wenn Client steht, Client erneut starten
1. Anzahl gelesene Nachrichten ansehen
1. Verzeichnis logs/node-0/bar-0 ansehen (Anzahl logs sollte wesentlich kleiner sein.)

# Troubleshoting

Delete: Alle Consumer/Producer auf dem topic beenden

server.log:
[2016-11-23 20:28:56,584] INFO Deleting segment 713755 from log bar-0. (kafka.log.Log)

ProducerConfig.BATCH_SIZE_CONFIG hat Einfluss auf die Anzahl der übrigbleibenden Einträge