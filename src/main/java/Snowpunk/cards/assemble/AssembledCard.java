package Snowpunk.cards.assemble;

import Snowpunk.cardmods.GearMod;
import Snowpunk.cardmods.HatMod;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.cards.abstracts.ClankCard;
import Snowpunk.cards.assemble.cores.Searing;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.util.AssembledCardArtRoller;
import basemod.abstracts.CustomSavable;
import basemod.helpers.CardModifierManager;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.patches.cardInterfaces.MultiUpgradePatches;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardSave;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

import static Snowpunk.SnowpunkMod.makeID;

@NoPools
@NoCompendium
public class AssembledCard extends AbstractMultiUpgradeCard implements CustomSavable<ArrayList<CardSave>>, ClankCard {
    public static final String ID = makeID(AssembledCard.class.getSimpleName());
    public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public ArrayList<CoreCard> cores;
    private final ArrayList<Integer> capturedIndices = new ArrayList<>();
    public int baseCost = 0, UP_DMG = -1, UP_BLOCK = -1, UP_MAGIC = -1, UP_M2 = -1;

    public AssembledCard() {
        this(new ArrayList<>());
    }

    public AssembledCard(ArrayList<CoreCard> newCores) {
        this(0, CardType.SKILL, CardRarity.RARE, CardTarget.NONE, newCores);
        tags.add(CardTags.HEALING); // We don't want this generated in combat
    }

    public AssembledCard(int cost, CardType type, CardRarity rarity, CardTarget target, ArrayList<CoreCard> newCores) {
        super(ID, cost, type, rarity, target);
        cores = newCores;
        grabStats();
    }

    //region Save & Load
    @Override
    public ArrayList<CardSave> onSave() {
        if (cores != null) {
            ArrayList<CardSave> cardSaves = new ArrayList<>();
            for (CoreCard card : cores)
                cardSaves.add(new CardSave(card.cardID, card.timesUpgraded, card.misc));

            return cardSaves;
        }
        return null;
    }

    @Override
    public void onLoad(ArrayList<CardSave> coreLoad) {
        if (coreLoad != null) {
            for (CardSave card : coreLoad) {
                if (card == null)
                    continue;
                AbstractCard baseCard = CardLibrary.getCard(card.id);
                CoreCard savedCard = (CoreCard) baseCard.makeStatEquivalentCopy();
                savedCard.timesUpgraded = card.upgrades;
                savedCard.misc = card.misc;
                //savedCard.applyPowers();
                savedCard.initializeDescription();
                cores.add((CoreCard) savedCard.makeStatEquivalentCopy());
            }
        }
        grabStats();
        for (int i : capturedIndices) {
            MultiUpgradePatches.MultiUpgradeFields.upgradeIndex.set(this, i);
            upgrade();
        }
    }


    @Override
    protected Texture getPortraitImage() {
        return AssembledCardArtRoller.getPortraitTexture(this);
    }
    //endregion

    //region Build Stats
    public void grabStats() {
        damage = baseDamage = getDamage();
        block = baseBlock = getBlock();
        magicNumber = baseMagicNumber = getMagic();
        secondMagic = baseSecondMagic = getMagic2();
        cost = baseCost = getCost();
        setCostForTurn(cost);

        if (isPower())
            type = CardType.POWER;
        else if (damage > 0)
            type = CardType.ATTACK;
        else
            type = CardType.SKILL;

        getTarget(damage);

        getSpecialStats();
        getName();
        getDescription();
        getGearsFromCores();

        if (!name.equals(""))
            AssembledCardArtRoller.computeCard(this);

        if (getUpgrades().size() == 0) {
            addUpgrades();
        }
    }

    private void getName() {
        name = "";
        for (CoreCard core : cores) {
            if (core instanceof AdjectiveCore) {
                name += AdjectiveCore.TEXT[core.misc];
                if (cores.indexOf(core) < cores.size() - 1 && !name.endsWith("-") && !cores.get(cores.size() - 1).name.startsWith("-"))
                    name += " ";
            }
            if (core instanceof NameCore)
                name += NameCore.TEXT[core.misc];
        }
        initializeTitle();
    }

    private void getDescription() {
        rawDescription = "{@@}";
        for (CoreCard core : cores) {
            if (!core.effectTags.contains(CoreCard.EffectTag.NAME) && !core.effectTags.contains(CoreCard.EffectTag.ADJECTIVE)) {
                rawDescription += core.rawDescription;
                if (!core.rawDescription.equals("")) {
                    //rawDescription += cardStrings.EXTENDED_DESCRIPTION[0];
                    if (cores.indexOf(core) <= 1 && core.rawDescription.length() > 1) {
                        if (core.rawDescription.endsWith("."))
                            rawDescription += " NL ";
                        else
                            rawDescription += " ";
                    }
                }
            }
        }
        initializeDescription();
    }
/*
    private boolean checkContinuations(CoreCard core) {
        if (cores.indexOf(core) == cores.size() - 1)
            return false;
        if ((core.effectTags.contains(CoreCard.EffectTag.AB)) && cores.get(cores.indexOf(core) + 1).effectTags.contains(CoreCard.EffectTag.ABMOD))
            return true;
        if ((core.damage > 0) && cores.get(cores.indexOf(core) + 1).effectTags.contains(CoreCard.EffectTag.AMOD))
            return true;
        if ((core.effectTags.contains(CoreCard.EffectTag.AMOD)) && cores.get(cores.indexOf(core) + 1).effectTags.contains(CoreCard.EffectTag.ABMOD))
            return true;
        if (cores.indexOf(core) == 0 && cores.size() > 2 && core.effectTags.contains(CoreCard.EffectTag.AB) && cores.get(1).rawDescription.equals("") && cores.get(2).effectTags.contains(CoreCard.EffectTag.ABMOD))
            return true;
        return false;
    }*/

    private int getDamage() {
        int totalDamage = 0;
        for (CoreCard core : cores) {
            if (core.baseDamage > 0) {
                totalDamage = core.baseDamage;
                if (core.getUpgradeAmount() > 0)
                    UP_DMG = core.getUpgradeAmount();
            }
        }
        return totalDamage > 0 ? totalDamage : -1;
    }

    private int getBlock() {
        int totalBlock = 0;
        for (CoreCard core : cores) {
            if (core.baseBlock > 0) {
                totalBlock += core.baseBlock;
                if (core.getUpgradeAmount() > 0)
                    UP_BLOCK = core.getUpgradeAmount();
            }
        }
        return totalBlock > 0 ? totalBlock : -1;
    }

    private int getMagic() {
        int totalMagic = 0;
        for (CoreCard core : cores) {
            if (core.baseMagicNumber > 0) {
                totalMagic += core.baseMagicNumber;
                if (core.getUpgradeAmount() > 0)
                    UP_MAGIC = core.getUpgradeAmount();
            }
        }
        return totalMagic > 0 ? totalMagic : -1;
    }

    private int getMagic2() {
        int totalMagic = 0;
        for (CoreCard core : cores) {
            if (core.baseSecondMagic > 0) {
                totalMagic += core.baseSecondMagic;
                if (core.getUpgradeAmount() > 0)
                    UP_M2 = core.getUpgradeAmount();
            }
        }
        return totalMagic > 0 ? totalMagic : -1;
    }

    public int getCost() {
        int totalCost = 0;
        for (CoreCard core : cores) {
            if (core.cost > -2)
                totalCost += core.cost;
        }
        return Math.max(totalCost, 0);
    }

    public boolean isPower() {
        for (CoreCard core : cores) {
            if (core.type == CardType.POWER)
                return true;
        }
        return false;
    }

    private void getGearsFromCores() {
        int numGears = 0;
        for (CoreCard core : cores) {
            if (CardModifierManager.hasModifier(core, GearMod.ID))
                numGears += ((GearMod) CardModifierManager.getModifiers(core, GearMod.ID).get(0)).amount;
        }
        if (numGears > 0) {
            if (CardModifierManager.hasModifier(this, GearMod.ID)) {
                int curGears = ((GearMod) CardModifierManager.getModifiers(this, GearMod.ID).get(0)).amount;
                if (numGears > curGears)
                    CardModifierManager.addModifier(this, new GearMod(numGears - curGears));
            } else
                CardModifierManager.addModifier(this, new GearMod(numGears));
        }
    }

    private void getTarget(int totalDamage) {
        target = CardTarget.SELF;
        if (totalDamage > 0) {
            if (cores.stream().anyMatch(c -> c.target == CardTarget.ALL_ENEMY)) {
                target = CardTarget.ALL_ENEMY;
                isMultiDamage = true;
            } else {
                target = CardTarget.ENEMY;
                isMultiDamage = false;
            }
        } else if (cores.stream().anyMatch(c -> c.target == CardTarget.ENEMY))
            target = CardTarget.ENEMY;
    }

    private void getSpecialStats() {
        for (CoreCard core : cores)
            core.setStats(this);
    }
    //endregion

    //region Core Methods
    @Override
    public void addUpgrades() {
        if (cores != null && cores.size() > 0) {
            int indexes = 0;
            boolean isSearing = cores.stream().anyMatch(coreCard -> coreCard instanceof Searing);
            if (baseDamage > 0) {
                int bonusDmg = UP_DMG;
                if (bonusDmg == -1)
                    bonusDmg = (int) Math.max(baseDamage * .33, 3);
                int finalBonusDmg = bonusDmg;
                addUpgradeData(() -> upgradeDamage(finalBonusDmg));
                indexes++;
                if (isSearing) {
                    for (int i = 0; i < 8; i++) {
                        addUpgradeData(() -> upgradeDamage(finalBonusDmg));
                        setDependencies(true, indexes, indexes - 1);
                        indexes++;
                    }
                }
            }
            if (baseBlock > 0) {
                int bonusBlock = UP_BLOCK;
                if (bonusBlock == -1)
                    bonusBlock = (int) Math.max(baseBlock * .25, 3);
                int finalBonusBlock = bonusBlock;
                addUpgradeData(() -> upgradeBlock(finalBonusBlock));
                indexes++;
                if (isSearing) {
                    for (int i = 0; i < 8; i++) {
                        addUpgradeData(() -> upgradeBlock(finalBonusBlock));
                        setDependencies(true, indexes, indexes - 1);
                        indexes++;
                    }
                }
            }
            if (baseMagicNumber > 0) {
                int bonusMagic = UP_MAGIC;
                if (bonusMagic == -1)
                    bonusMagic = (int) Math.max(1, baseMagicNumber * .5);
                int finalBonusMagic = bonusMagic;
                addUpgradeData(() -> upgradeMagicNumber(finalBonusMagic));
                indexes++;
            }
            if (baseSecondMagic > 0) {
                int bonusMagic = UP_M2;
                if (bonusMagic == -1)
                    bonusMagic = (int) Math.max(1, baseSecondMagic * .5);
                int finalBonusMagic = bonusMagic;
                addUpgradeData(() -> upgradeSecondMagic(finalBonusMagic));
                indexes++;
            }

            int numGearUp = 0;
            for (CoreCard core : cores) {
                if (core.gearUpgrade()) {
                    numGearUp++;
                    addUpgradeData(() -> CardModifierManager.addModifier(this, new GearMod(core.getUpgradeAmount())));
                    if (numGearUp > 1)
                        setDependencies(true, indexes, indexes - 1);
                    indexes++;
                }
            }

            //TEMPS
            int hotUp = indexes;
            if (type != CardType.POWER) {
                addUpgradeData(() -> CardTemperatureFields.addInherentHeat(this, CardTemperatureFields.HOT * 2));
                indexes++;
            }
            int coldUp = indexes;
            addUpgradeData(() -> CardTemperatureFields.addInherentHeat(this, CardTemperatureFields.COLD));
            indexes++;
            if (type != CardType.POWER)
                setExclusions(hotUp, coldUp);


            int numCostDown = 0;
            for (CoreCard core : cores) {
                if (core.costUpgrade()) {
                    numCostDown++;
                    int finalNumCostDown = numCostDown;
                    addUpgradeData(() -> upgradeBaseCost(baseCost - finalNumCostDown));
                    if (numCostDown > 1)
                        setDependencies(true, indexes, indexes - 1);
                    indexes++;
                }
            }


            //HAT
            if (getCost() > 0 && !exhaust && type != CardType.POWER) {
                addUpgradeData(() -> CardModifierManager.addModifier(this, new HatMod()));
                indexes++;
            }
//            if (baseCost - numCostDown > 0) {
//                numCostDown++;
//                int finalNumCostDown = numCostDown;
//                addUpgradeData(() -> upgradeBaseCost(baseCost - finalNumCostDown));
//                if (numCostDown > 1)
//                    setDependencies(true, indexes, indexes - 1);
//                indexes++;
//            }
        }
    }


    @Override
    public void upgrade() {
        if (this.getUpgrades().isEmpty()) {
            capturedIndices.add(MultiUpgradePatches.MultiUpgradeFields.upgradeIndex.get(this));
        } else {
            super.upgrade();
        }
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        for (CoreCard coreCard : cores) {
            coreCard.onUseEffect(player, monster, this);
        }
    }

    public void getModifiedDamageAction(AbstractPlayer player, AbstractMonster monster) {
        for (CoreCard coreCard : cores) {
            //Check Damage Mod Stuff Here
        }
        if (target == CardTarget.ENEMY)
            dmg(monster, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL);
        /*else if (target == CardTarget.ALL_ENEMY) {
            if (cores.stream().anyMatch(coreCard -> coreCard.effectTags.contains(CoreCard.EffectTag.ALLDmg)))
                allDmg(AbstractGameAction.AttackEffect.SLASH_HORIZONTAL);
            else {
                AbstractMonster cardTarget = AbstractDungeon.getRandomMonster();
                if (cardTarget != null)
                    dmg(cardTarget, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL);
            }
        }*/
    }
    //endregion

    @Override
    public AbstractCard makeCopy() {
        ArrayList<CoreCard> newCores = new ArrayList<>();
        for (AbstractCard core : cores) {
            newCores.add((CoreCard) core.makeStatEquivalentCopy());
        }
        return new AssembledCard(newCores);
    }

    @Override
    public void onClank(AbstractMonster monster) {
        for (CoreCard coreCard : cores)
            coreCard.onClank(this);
    }
}
