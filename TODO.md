# Bee Industry - TODO & Ideen
Hier ist eine strukturierte Übersicht der geplanten Features und Konzepte für die Bee Industry Mod.

## Bienen-Konzepte
### Ressourcen-Bienen
- [ ] **Basis-Ressourcen:**
  - [x] Dirt-Biene
  - [x] Sand-Biene
  - [x] Gravel-Biene
  - [x] Stone-Biene
  - [x] Mossy-Biene (Moos)
- [ ] **Holz-Biene:**
  - [ ] Implementierung einer generischen Holz-Biene, deren Holz-Typ vom Bestäubungsblock abhängt (z.B. Eichensetzling -> Eichenholz).
- [ ] **Mineral-Bienen:**
  - [x] Coal-Biene (Kohle)
  - [x] Iron-Biene (Eisen)
  - [x] Copper-Biene
  - [x] Gold-Biene
  - [ ] Redstone-Biene
  - [ ] Lapis-Biene
  - [x] Diamond-Biene
  - [x] Emerald-Biene
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
- [x] **Mining-Biene:** Baut in einem bestimmten Bereich Blöcke ab (eventuell in Verbindung mit dem "Beepost").
- [x] **Farming-Biene:** Erntet reife Feldfrüchte in einem bestimmten Bereich.
- [ ] **Fishing-Biene:** Fischt in einem nahegelegenen Gewässer.
- [x] **Fighting-Biene:** Verteidigt einen Bereich oder den Spieler.

### Sonstige Bienen
- [ ] **Reitbare Biene:** Eine größere Biene, auf der der Spieler reiten kann.
- [ ] **Licht-Biene:** Eine Biene, die Licht aussendet.

### Zucht & Mutationen
- [x] System für Bienen-Mutationen entwerfen.
- [ ] **High-Level-Idee:** Ein Genetik-System, ähnlich wie bei Jurassic Park, um Bienen zu verändern.

## Blöcke & Maschinen

### Verbesserter Bienenstock (Advanced Beehive)
- [x] Eigenen Bienenstock-Block erstellen, der als Basis für Upgrades dient.
- [x] **Upgrade-System:**
  - [ ] **Size-Upgrade:** Erhöht die Kapazität des Stocks (+1/2/3/5 Bienen).
  - [x] **Effizienz-Upgrade:** Bienen benötigen weniger Zeit im Stock (5/10/15/25 % schneller).
  - [x] **Mengen-Upgrade:** Chance auf doppelte Item-Menge (20/40/60/90 %).

### Beepost (Arbeitsstation für Bienen)
- [x] **Block "Beepost" erstellen:**
  - [x] Dient als Ausgangspunkt und Hub für Arbeiterbienen.
  - [x] Bienen operieren in einem definierten Bereich um den Block (z.B. 10x10x5).
  - [x] Benötigt "SweetHoney" als Treibstoff.
- [x] **GUI für den Beepost:**
  - [x] Entwerfen basierend auf der Skizze.
  - [x] Slots für bis zu 3 Bienen (via Bienen-Container).
  - [x] Aktivierungs-/Deaktivierungs-Schalter für jede Biene.
  - [x] Slots für bienenspezifische Upgrades (z.B. Filter, Effizienz).
  - [x] Slots für Block-Upgrades (z.B. Reichweite).
  - [x] Anzeige für den "SweetHoney"-Treibstoff.

### Welt-generierte Nester
- [ ] Nester für Basis-Bienen (Dirt, Stone, Sand, Gravel) in der Welt spawnen lassen.
- [ ] Unterschiedliche Holz-Nester für die Holz-Biene in den entsprechenden Biomen (Eiche, Birke etc.).

### Sonstige Blöcke
- [ ] **Mögliche Idee:** Eigene Werkbank zum Herstellen von Charms.

## Items

### Rüstung (Beekeeper Armor)
- [x] **Set-Bonus:** Bienen werden nicht mehr aggressiv bei Zerstörung von Nestern oder Angriffen.
- [x] **Herstellung:** Basis Lederrüstung + Honig.
- [x] **Einzelteil-Boni:**
  - [x] **Helm:** Bienen verlieren ihre Aggressivität 90 % schneller.
  - [x] **Brustplatte:** Erhöht den Anziehungsradius für Bienen um 5 Blöcke.
  - [x] **Hose:** Löst bei erlittenem Schaden einen Knochenmehl-Effekt in der Umgebung aus.
  - [x] **Schuhe:** +5 % Geschwindigkeit auf Gras, reduziert Fallschaden leicht.

### Talismane (Charms)
- [x] **Charm-Template** als Basis-Crafting-Item.
- [ ] **Effizienz-Charm:** (Rezept: 1x Template, 4x Goldblöcke, ...).
- [ ] **Glücks-Charm:** (Rezept: 1x Template, 1x Diamantblock, 1x Smaragdblock, ...).
- [ ] **Pollination-Charm:** Garantiert eine Zusatzressource neben der Hauptressource.
- [ ] **Babee-Charm:** Erhöht die Chance auf Mutationen bei der Zucht.
- [ ] **Honey-Charm:** Erhöht die Honigproduktion.
- [x] **Night-Charm:** Lässt Bienen auch nachts arbeiten.
- [ ] **Rainy-Charm:** Lässt Bienen auch bei Regen arbeiten.
- [ ] **Speed-Charm:** Arbeiterbienen fliegen schneller.

### Sonstige Items
- [x] **Bienen-Container:** Item, um Bienen einzufangen und im Beepost zu platzieren.
- [x] **SweetHoney:** Treibstoff für den Beepost.

## Gameplay-Mechaniken & World-Integration

### Bestäubungs-Mechanik (Pollination)
- [x] **Kerndesign-Entscheidung:** Anstatt neuer Blumen, werden Blöcke zur "Bestäubung" und Produktion verwendet (z.B. Eisenblock für Eisenbiene). Dies muss für jede Biene definiert werden.

### Villager
- [x] **Neuer Beruf: Beekeeper (Imker):**
  - [x] Arbeitsstation: Advanced Beehive.
  - [x] Handelt mit Spawn-Eggs, Honig und Bienen-Containern.

## Offene Design-Fragen & Konzepte
- [ ] **Holz-Bienen:** Wie genau wird unterschieden, welches Holz produziert wird? Abhängig vom nächstgelegenen Setzling/Holzblock?
- [x] **Beepost-Design:** Wie soll der Block genau aussehen?
- [ ] **Reitbare Biene:** Wie wird sie gesteuert? Hat sie besondere Fähigkeiten?
- [ ] **Balancing:** Wie werden die Produktionsraten, Upgrade-Kosten und Zucht-Chancen ausbalanciert, um fair und motivierend zu sein?
- [ ] **Crafting-Rezepte:** Detaillierte Rezepte für alle Items und Blöcke festlegen.

# BUGS
- [x] Bee Container wenn man mit einer Biene drin eine andere rechtsklick, wird die neue eingesammelt und die alte einfach gelöscht.

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

