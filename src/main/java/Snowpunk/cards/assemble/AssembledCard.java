package Snowpunk.cards.assemble;

import Snowpunk.cardmods.TemperatureMod;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.cards.interfaces.OnRecreateCardModsCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.patches.MultiUpgradePatches;
import Snowpunk.util.AssembledCardArtRoller;
import Snowpunk.util.UpgradeData;
import Snowpunk.util.UpgradeRunnable;
import Snowpunk.util.Wiz;
import basemod.abstracts.CustomSavable;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardSave;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

import static Snowpunk.SnowpunkMod.makeID;

@NoPools
@NoCompendium
public class AssembledCard extends AbstractMultiUpgradeCard implements OnRecreateCardModsCard, CustomSavable<ArrayList<CardSave>> {
    public static final String ID = makeID(AssembledCard.class.getSimpleName());

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

    @Override
    protected Texture getPortraitImage() {
        return AssembledCardArtRoller.getPortraitTexture(this);
    }

    @Override
    public void addUpgrades() {
        //if(cores != null && cores.size() > 0){
        //if(baseDamage > 0){
        int bonusDmg = (int) Math.max(baseDamage * .33, 3);
        addUpgradeData(() -> upgradeDamage(bonusDmg));
//            }
//            if(baseBlock > 0){
        int bonusBlock = (int) Math.max(baseBlock * .25, 3);
        addUpgradeData(() -> upgradeBlock(bonusBlock));
//            }
//            if(baseMagicNumber > 0)
        addUpgradeData(() -> upgradeMagicNumber(1));
//            if(baseSecondMagic > 0)
        addUpgradeData(() -> upgradeSecondMagic(1));
//            if(CardTemperatureFields.getCardHeat(this) == 1)
        addUpgradeData(() -> CardTemperatureFields.addInherentHeat(this, 1));
//            if(CardTemperatureFields.getCardHeat(this) == -1)
        addUpgradeData(() -> CardTemperatureFields.addInherentHeat(this, -1));
//            if(baseCost > 1)
        addUpgradeData(() -> upgradeBaseCost(baseCost - 1));
//        }
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
            Wiz.atb(new DamageAction(monster, new DamageInfo(player, damage)));
        else if (target == CardTarget.ALL_ENEMY)
            allDmg(AbstractGameAction.AttackEffect.SLASH_HORIZONTAL);
    }

    //region Save & Load
    @Override
    public ArrayList<CardSave> onSave() {
        if (cores != null) {
            ArrayList<CardSave> cardSaves = new ArrayList<>();
            cardSaves.add(new CardSave(cardID, timesUpgraded, misc));
            for (CoreCard card : cores)
                cardSaves.add(new CardSave(card.cardID, card.timesUpgraded, card.misc));

            return cardSaves;
        }
        return null;
    }

    @Override
    public void onLoad(ArrayList<CardSave> coreLoad) {
        int timesToUpgrade = 0;
        if (coreLoad != null) {
            if (coreLoad.get(0).id == cardID) {
                timesToUpgrade = coreLoad.get(0).upgrades;
                coreLoad.remove(coreLoad.get(0));
            }
            for (CardSave card : coreLoad) {
                if (card == null)
                    continue;
                CoreCard savedCard = (CoreCard) CardLibrary.getCard(card.id);
                savedCard.timesUpgraded = card.upgrades;
                savedCard.misc = card.misc;

                cores.add(savedCard);
            }
        }
        grabStats();
        timesUpgraded = timesToUpgrade;
    }


    @Override
    public void onRecreate() {
        for (int i : capturedIndices) {
            MultiUpgradePatches.MultiUpgradeFields.upgradeIndex.set(this, i);
            upgrade();
        }
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

        if (name != "")
            AssembledCardArtRoller.computeCard(this);

        if (getUpgrades().size() == 0) {
            addUpgrades();
        }
    }

    private void getName() {
        name = "";
        for (CoreCard core : cores) {
            name += core.name;
            if (cores.indexOf(core) < cores.size() - 1)
                name += " ";
        }
        initializeTitle();
    }

    private void getDescription() {
        rawDescription = "";
        for (CoreCard core : cores) {
            rawDescription += core.rawDescription;
            if (cores.indexOf(core) < cores.size() - 1 && core.rawDescription.length() > 1)
                rawDescription += " NL ";
        }
        initializeDescription();
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

    @Override
    public AbstractCard makeCopy() {
        return makeStatEquivalentCopy();
    }

    @Override
    public AbstractCard makeStatEquivalentCopy() {
        ArrayList<CoreCard> newCores = new ArrayList<>();
        newCores.addAll(cores);
        AbstractCard copy = new AssembledCard(newCores);
        copy.timesUpgraded = timesUpgraded;
        return copy;
    }
}
