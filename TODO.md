# Bee Industry - TODO & Ideen
Hier ist eine strukturierte Übersicht der geplanten Features und Konzepte für die Bee Industry Mod.

## Bienen-Konzepte
### Ressourcen-Bienen
- [ ] **Basis-Ressourcen:**
  - [ ] Dirt-Biene
  - [ ] Sand-Biene
  - [ ] Gravel-Biene
  - [ ] Stone-Biene
  - [ ] Mossy-Biene (Moos)
- [ ] **Holz-Biene:**
  - [ ] Implementierung einer generischen Holz-Biene, deren Holz-Typ vom Bestäubungsblock abhängt (z.B. Eichensetzling -> Eichenholz).
- [ ] **Mineral-Bienen:**
  - [ ] Coal-Biene (Kohle)
  - [ ] Iron-Biene (Eisen)
  - [ ] Copper-Biene
  - [ ] Gold-Biene
  - [ ] Redstone-Biene
  - [ ] Lapis-Biene
  - [ ] Diamond-Biene
  - [ ] Emerald-Biene
  - [ ] Amethyst-Biene
- [ ] **Nether-Bienen:**
  - [ ] Netherrack-Biene
  - [ ] Quartz-Biene
  - [ ] Bone-Biene (Knochen)
  - [ ] Slime-Biene
  - [ ] Netherite-Biene (Netherite-Schrott)
- [ ] **End-Bienen:**
  - [ ] Endstone-Biene
- [ ] **Wasser/Ozean-Bienen:**
  - [ ] Coral-Biene
  - [ ] Aquamarin-Biene (Prismarin)
  - [ ] Water-Biene
- [ ] **Spezial-Bienen:**
  - [ ] Lava-Biene
  - [ ] Honey-Biene
  - [ ] Obsidian-Biene

### Arbeiterbienen
- [ ] **Mining-Biene:** Baut in einem bestimmten Bereich Blöcke ab (eventuell in Verbindung mit dem "Beepost").
- [ ] **Farming-Biene:** Erntet reife Feldfrüchte in einem bestimmten Bereich.
- [ ] **Fishing-Biene:** Fischt in einem nahegelegenen Gewässer.
- [ ] **Fighting-Biene:** Verteidigt einen Bereich oder den Spieler.

### Sonstige Bienen
- [ ] **Reitbare Biene:** Eine größere Biene, auf der der Spieler reiten kann.
- [ ] **Licht-Biene:** Eine Biene, die Licht aussendet.

### Zucht & Mutationen
- [ ] System für Bienen-Mutationen entwerfen.
- [ ] **High-Level-Idee:** Ein Genetik-System, ähnlich wie bei Jurassic Park, um Bienen zu verändern.

## Blöcke & Maschinen

### Verbesserter Bienenstock (Advanced Beehive)
- [ ] Eigenen Bienenstock-Block erstellen, der als Basis für Upgrades dient.
- [ ] **Upgrade-System:**
  - [ ] **Size-Upgrade:** Erhöht die Kapazität des Stocks (+1/2/3/5 Bienen).
  - [ ] **Effizienz-Upgrade:** Bienen benötigen weniger Zeit im Stock (5/10/15/25 % schneller).
  - [ ] **Mengen-Upgrade:** Chance auf doppelte Item-Menge (20/40/60/90 %).

### Beepost (Arbeitsstation für Bienen)
- [ ] **Block "Beepost" erstellen:**
  - [ ] Dient als Ausgangspunkt und Hub für Arbeiterbienen.
  - [ ] Bienen operieren in einem definierten Bereich um den Block (z.B. 10x10x5).
  - [ ] Benötigt "SweetHoney" als Treibstoff.
- [ ] **GUI für den Beepost:**
  - [ ] Entwerfen basierend auf der Skizze.
  - [ ] Slots für bis zu 3 Bienen (via Bienen-Container).
  - [ ] Aktivierungs-/Deaktivierungs-Schalter für jede Biene.
  - [ ] Slots für bienenspezifische Upgrades (z.B. Filter, Effizienz).
  - [ ] Slots für Block-Upgrades (z.B. Reichweite).
  - [ ] Anzeige für den "SweetHoney"-Treibstoff.

### Welt-generierte Nester
- [ ] Nester für Basis-Bienen (Dirt, Stone, Sand, Gravel) in der Welt spawnen lassen.
- [ ] Unterschiedliche Holz-Nester für die Holz-Biene in den entsprechenden Biomen (Eiche, Birke etc.).

### Sonstige Blöcke
- [ ] **Mögliche Idee:** Eigene Werkbank zum Herstellen von Charms.

## Items

### Rüstung (Beekeeper Armor)
- [ ] **Set-Bonus:** Bienen werden nicht mehr aggressiv bei Zerstörung von Nestern oder Angriffen.
- [ ] **Herstellung:** Basis Lederrüstung + Honig.
- [ ] **Einzelteil-Boni:**
  - [ ] **Helm:** Bienen verlieren ihre Aggressivität 90 % schneller.
  - [ ] **Brustplatte:** Erhöht den Anziehungsradius für Bienen um 5 Blöcke.
  - [ ] **Hose:** Löst bei erlittenem Schaden einen Knochenmehl-Effekt in der Umgebung aus.
  - [ ] **Schuhe:** +5 % Geschwindigkeit auf Gras, reduziert Fallschaden leicht.

### Talismane (Charms)
- [ ] **Charm-Template** als Basis-Crafting-Item.
- [ ] **Effizienz-Charm:** (Rezept: 1x Template, 4x Goldblöcke, ...).
- [ ] **Glücks-Charm:** (Rezept: 1x Template, 1x Diamantblock, 1x Smaragdblock, ...).
- [ ] **Pollination-Charm:** Garantiert eine Zusatzressource neben der Hauptressource.
- [ ] **Babee-Charm:** Erhöht die Chance auf Mutationen bei der Zucht.
- [ ] **Honey-Charm:** Erhöht die Honigproduktion.
- [ ] **Night-Charm:** Lässt Bienen auch nachts arbeiten.
- [ ] **Rainy-Charm:** Lässt Bienen auch bei Regen arbeiten.
- [ ] **Speed-Charm:** Arbeiterbienen fliegen schneller.

### Sonstige Items
- [ ] **Bienen-Container:** Item, um Bienen einzufangen und im Beepost zu platzieren.
- [ ] **SweetHoney:** Treibstoff für den Beepost.

## Gameplay-Mechaniken & World-Integration

### Bestäubungs-Mechanik (Pollination)
- [ ] **Kerndesign-Entscheidung:** Anstatt neuer Blumen, werden Blöcke zur "Bestäubung" und Produktion verwendet (z.B. Eisenblock für Eisenbiene). Dies muss für jede Biene definiert werden.

### Villager
- [ ] **Neuer Beruf: Beekeeper (Imker):**
  - [ ] Arbeitsstation: Advanced Beehive.
  - [ ] Handelt mit Spawn-Eggs, Honig und Bienen-Containern.

## Offene Design-Fragen & Konzepte
- [ ] **Holz-Bienen:** Wie genau wird unterschieden, welches Holz produziert wird? Abhängig vom nächstgelegenen Setzling/Holzblock?
- [ ] **Beepost-Design:** Wie soll der Block genau aussehen?
- [ ] **Reitbare Biene:** Wie wird sie gesteuert? Hat sie besondere Fähigkeiten?
- [ ] **Balancing:** Wie werden die Produktionsraten, Upgrade-Kosten und Zucht-Chancen ausbalanciert, um fair und motivierend zu sein?
- [ ] **Crafting-Rezepte:** Detaillierte Rezepte für alle Items und Blöcke festlegen.

---
# ALT
- Bienenarten für verschiedene Ressourcen:
    - Eisenbiene -> Eisenbrocken/Eisenstaub
    - Redstonebiene -> Redstonestaub
    - Lapis-Biene -> Lapis Lazuli
    - Kohle-Biene -> Kohlestaub
    - Diamantbiene -> Seltene Biene, Kohle als Rest, Diamantkristalle als Main
  
- Bienen Stufen und Mutationen
  - Bronzebiene -> Eisen & Kupferbiene
  - Platinbiene -> Goldbiene Mutation

- Bienenstöcke mit Upgrades
  - Lagererweiterung -> Erhöht Kapazität
  - Produktionsboost -> Erhöht Geschwindigkeit
  - Automatisierungen -> "Förderbänder"/"Hopper"

- Biom Specific Bienen
  - Sandbiene -> Wüste - Sand/Glas
  - Netherbiene -> Nether - Netherite Shards/Quarz
  - Tiefseebiene - Prismarin

- Arbeiterkolonien #SOON

- Bienen-Energie
  - Energiestöcke -> Keine Ressourcen, dafür Energie
  - Energiegeneratoren mit Bienenantrieb

- Bienenkönigin
  - Eisenkönigin -> Erhöht Produktion von Eisenbienen
  - Goldkönigin -> Erhöht Geschwindigkeit der Bienen

- Maschinen
  - Quarry/Bohrer mit Bienen
  - Nektarveredelung -> Nektar upgraden für mehr Drops??

- Gefahren durch Bienen
  - Zu viel Arbeit/Druck -> aggressiv/stechen

