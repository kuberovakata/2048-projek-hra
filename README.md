#  2 0 4 8

Hra vznikla za účelem školního projektu druhého ročniku.

Klasická logická hra **2048** v moderním kabátě s grafickým rozhraním a **unikátní mechanikou prohazování dlaždic**.

---

## start pro hráče

Chceš si hru hned zahrát? Stačí dodržet tyto 2 jednoduché kroky:

### 1. Předpoklady
Pro spuštění hry potřebuješ mít v počítači nainstalovanou **Javu (verzi 8 nebo novější)**. Pokud ji ještě nemáš, můžeš si ji zdarma stáhnout z oficiálních stránek [java.com](https://www.java.com).

### 2. Stažení a spuštění
1. Stáhni si soubor **`2048.jar`** **.
2. **Dvakrát na stažený soubor klikni** a hra se automaticky spustí.

>  **Nefunguje dvojklik?**
> Pokud se hra po dvojkliku nespustí, otevři příkazovou řádku (Terminál / CMD) ve složce se staženým souborem a zadej příkaz:
> ```bash
> java -jar 2048.jar
> ```

---

##  Co tě ve hře čeká?

* **📐 Volba velikosti pole:** V hlavním menu si můžeš vybrat, jak velkou výzvu chceš. Na výběr je klasické pole **4x4**, střední **6x6** nebo obří **8x8**.
* **🔮 Bonusový systém (Prohazování):** * Za každých nasbíraných **1 000 bodů** získáš jedno bonusové prohození.
  * Tento bonus ti umožní zachránit se před prohrou – stačí myší kliknout na dvě dlaždice a vyměnit jejich pozice!

---

##  Ovládání hry

* **Pohyb dlaždic:** Používej **šipky** na klávesnici (Nahoru, Vlevo, Dolů, Vpravo).
* **Použití bonusu (Prohození):** 1. Pokud máš na kontě alespoň `1x Prohození`, klikni myší na první dlaždici (vizuálně se označí).
  2. Klikni na druhou dlaždici, se kterou chceš tu první vyměnit.
  3. Hodnoty se prohodí a odečte se ti jeden bonus.
* **Zavření hry:** Při kliknutí na křížek tě hra nenechá omylem ztratit rozehranou partii – zobrazí se potvrzovací okýnko s dotazem, zda se chceš opravdu vrátit do menu.

---

##  Pro vývojáře (Struktura kódu)

Pokud tě zajímá, jak je hra napsaná uvnitř, projekt striktně dodržuje architekturu **MVC (Model-View-Controller)**:
* **`Hra.java`** & **`HraciPole.java`** (Model) – Správa matematické logiky, posunů a generování čísel.
* **`Okno.java`**, **`HlavniMenu.java`** & **`Dlazdice.java`** (View) – Grafické rozhraní postavené na Java Swing. Načítání obrázků probíhá interně pomocí `getClass().getResource()`.
* **`Ovladani.java`** (Controller) – Propojení mezi logikou a grafikou, zpracování uživatelských vstupů.
