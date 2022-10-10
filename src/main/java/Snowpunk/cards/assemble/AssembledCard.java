package Snowpunk.cards.assemble;

import Snowpunk.cardmods.FrostMod;
import Snowpunk.cardmods.TemperatureMod;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.cards.assemble.cores.AllEnemiesCore;
import Snowpunk.cards.assemble.cores.TwiceCore;
import Snowpunk.cards.interfaces.OnRecreateCardModsCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.patches.MultiUpgradePatches;
import Snowpunk.util.AssembledCardArtRoller;
import Snowpunk.util.UpgradeData;
import Snowpunk.util.UpgradeRunnable;
import Snowpunk.util.Wiz;
import basemod.abstracts.CustomSavable;
import basemod.helpers.CardModifierManager;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardSave;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;
import java.util.Objects;

import static Snowpunk.SnowpunkMod.makeID;

@NoPools
@NoCompendium
public class AssembledCard extends AbstractMultiUpgradeCard implements CustomSavable<ArrayList<CardSave>> {
    public static final String ID = makeID(AssembledCard.class.getSimpleName());
    public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public ArrayList<CoreCard> cores;
    private final ArrayList<Integer> capturedIndices = new ArrayList<>();
    public int baseCost = 0;

    public AssembledCard() {
        this(new ArrayList<>());
    }

    public AssembledCard(ArrayList<CoreCard> newCores) {
        this(0, CardType.SKILL, CardRarity.SPECIAL, CardTarget.NONE, newCores);
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
                savedCard.applyPowers();
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

        if (damage > 0)
            type = CardType.ATTACK;
        else
            type = CardType.SKILL;

        getTarget(damage);

        getSpecialStats();
        getName();
        getDescription();

        if (!name.equals("") && name != null)
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
        rawDescription = "";
        for (CoreCard core : cores) {
            if (!core.effectTags.contains(CoreCard.EffectTag.NAME) && !core.effectTags.contains(CoreCard.EffectTag.ADJECTIVE)) {
                rawDescription += core.rawDescription;
                if (!checkContinuations(core) && !core.rawDescription.equals("")) {
                    rawDescription += cardStrings.EXTENDED_DESCRIPTION[0];
                    if (cores.indexOf(core) < cores.size() - 1 && core.rawDescription.length() > 1)
                        rawDescription += " NL ";
                }
            }
        }
        initializeDescription();
    }

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
    }

    private int getDamage() {
        int totalDamage = 0;
        for (CoreCard core : cores) {
            if (core.baseDamage > 0)
                totalDamage += core.baseDamage;
        }
        return totalDamage > 0 ? totalDamage : -1;
    }

    private int getBlock() {
        int totalBlock = 0;
        for (CoreCard core : cores) {
            if (core.baseBlock > 0)
                totalBlock += core.baseBlock;
        }
        return totalBlock > 0 ? totalBlock : -1;
    }

    private int getMagic() {
        int totalMagic = 0;
        for (CoreCard core : cores) {
            if (core.baseMagicNumber > 0)
                totalMagic += core.baseMagicNumber;
        }
        return totalMagic > 0 ? totalMagic : -1;
    }

    private int getMagic2() {
        int totalMagic = 0;
        for (CoreCard core : cores) {
            if (core.baseSecondMagic > 0)
                totalMagic += core.baseSecondMagic;
        }
        return totalMagic > 0 ? totalMagic : -1;
    }

    private int getCost() {
        int totalCost = 0;
        for (CoreCard core : cores) {
            if (core.cost > -2)
                totalCost += core.cost;
        }
        return Math.max(totalCost, 0);
    }

    private void getTarget(int totalDamage) {
        target = CardTarget.SELF;
        if (totalDamage > 0) {
            if (cores.stream().anyMatch(c -> c.target == CardTarget.ALL_ENEMY)) {
                target = CardTarget.ALL_ENEMY;
                if (cores.stream().anyMatch(coreCard -> coreCard instanceof AllEnemiesCore))
                    isMultiDamage = true;
            } else {
                target = CardTarget.ENEMY;
                isMultiDamage = false;
            }
        }
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
            if (baseDamage > 0) {
                int bonusDmg = (int) Math.max(baseDamage * .33, 3);
                addUpgradeData(() -> upgradeDamage(bonusDmg));
            }
            if (baseBlock > 0) {
                int bonusBlock = (int) Math.max(baseBlock * .25, 3);
                addUpgradeData(() -> upgradeBlock(bonusBlock));
            }
            if (baseMagicNumber > 0) {
                int bonusMagic = (int) Math.max(1, baseMagicNumber * .5);
                addUpgradeData(() -> upgradeMagicNumber(bonusMagic));
            }
            if (baseSecondMagic > 0) {
                int bonusMagic = (int) Math.max(1, baseSecondMagic * .5);
                addUpgradeData(() -> upgradeSecondMagic(bonusMagic));
            }
            if (CardModifierManager.hasModifier(this, FrostMod.ID))
                addUpgradeData(() -> CardTemperatureFields.addInherentHeat(this, -1));
            if (baseCost > 2)
                addUpgradeData(() -> upgradeBaseCost(baseCost - 1));
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
        else if (target == CardTarget.ALL_ENEMY) {
            if (cores.stream().anyMatch(coreCard -> coreCard instanceof AllEnemiesCore))
                allDmg(AbstractGameAction.AttackEffect.SLASH_HORIZONTAL);
            else
                dmg(AbstractDungeon.getRandomMonster(), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL);
        }
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
}
