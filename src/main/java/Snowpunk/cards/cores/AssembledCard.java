package Snowpunk.cards.cores;

import Snowpunk.cardmods.BetterExhaustMod;
import Snowpunk.cardmods.TinkerSelfMod;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.cards.cores.util.OnUseCardInstance;
import Snowpunk.cards.interfaces.OnRecreateCardModsCard;
import Snowpunk.patches.MultiUpgradePatches;
import Snowpunk.util.AssembledCardArtRoller;
import Snowpunk.util.Triplet;
import basemod.Pair;
import basemod.abstracts.CustomSavable;
import basemod.helpers.CardModifierManager;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;
import java.util.Collections;
import java.util.function.Consumer;

import static Snowpunk.SnowpunkMod.makeID;

@NoPools @NoCompendium
public class AssembledCard extends AbstractMultiUpgradeCard implements OnRecreateCardModsCard, CustomSavable<AssembledCard.SaveInfo> {
    public static final String ID = makeID(AssembledCard.class.getSimpleName());

    private final ArrayList<Consumer<AssembledCard>> onUpgradeConsumers = new ArrayList<>();
    private final ArrayList<OnUseCardInstance> onUseInstances = new ArrayList<>();
    public int doubledCost;

    private final ArrayList<Integer> capturedIndices = new ArrayList<>();

    private SaveInfo info = new SaveInfo();

    public AssembledCard() {
        this(0, CardType.SKILL, CardRarity.SPECIAL, CardTarget.NONE);
    }

    public AssembledCard(int cost, CardType type, CardRarity rarity, CardTarget target) {
        super(ID, cost, type, rarity, target);
    }

    @Override
    protected Texture getPortraitImage() {
        return AssembledCardArtRoller.getPortraitTexture(this);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (OnUseCardInstance i : onUseInstances) {
            i.onUseConsumer.accept(p, m);
        }
    }

    @Override
    public void upgrade() {
        if (this.getUpgrades(this).isEmpty()) {
            capturedIndices.add(MultiUpgradePatches.MultiUpgradeFields.upgradeIndex.get(this));
        } else {
            super.upgrade();
        }
    }

    @Override
    public void upp() {
        for (Consumer<AssembledCard> c : onUpgradeConsumers) {
            c.accept(this);
        }
    }

    public void addUpgradeConsumer(Consumer<AssembledCard> c) {
        onUpgradeConsumers.add(c);
    }

    public void addUseEffects(OnUseCardInstance i) {
        onUseInstances.add(i);
        Collections.sort(onUseInstances);
    }

    public void addDamageUpgrade(int amount, boolean secondaryVar) {
        if (secondaryVar) {
            addUpgradeData(this, () -> upgradeSecondDamage(amount));
        } else {
            addUpgradeData(this, () -> upgradeDamage(amount));
        }
    }

    public void addBlockUpgrade(int amount, boolean secondaryVar) {
        if (secondaryVar) {
            addUpgradeData(this, () -> upgradeSecondBlock(amount));
        } else {
            addUpgradeData(this, () -> upgradeBlock(amount));
        }
    }

    public void addMagicUpgrade(int amount, boolean secondaryVar) {
        if (secondaryVar) {
            addUpgradeData(this, () -> upgradeSecondMagic(amount));
        } else {
            addUpgradeData(this, () -> upgradeMagicNumber(amount));
        }
    }

    public void addInfo(Triplet<SaveInfo.CoreType, Boolean, Integer> t) {
        info.addCore(t);
        switch (t.getKey()) {
            case BRASS_SHIELD:
                addBlockUpgrade(t.getValue(), t.getFlag());
                break;
            case BURNING_ENGINE:
                addMagicUpgrade(t.getValue(), t.getFlag());
                break;
            case DOUBLE_BARREL:
                addDamageUpgrade(t.getValue(), t.getFlag());
                break;
            case FLING_SCRAP:
                addDamageUpgrade(t.getValue(), t.getFlag());
                break;
            case FLING_SCRAP2:
                addMagicUpgrade(t.getValue(), t.getFlag());
                break;
            case FLUX_COMBOBULATOR:
                addMagicUpgrade(t.getValue(), t.getFlag());
                addUpgradeData(this, () -> {
                    this.exhaust = false;
                    CardModifierManager.removeModifiersById(this, BetterExhaustMod.ID, false);
                });
                break;
            case FLUX_MACHINE:
                addMagicUpgrade(t.getValue(), t.getFlag());
                break;
            case MONKEY_WRENCH:
                if (t.getFlag()) {
                    if (t.getValue() == 1) {
                        addUpgradeData(this, () -> {
                            upgradeSecondBlock(MonkeyWrench.UP_BLOCK);
                            upgradeSecondDamage(MonkeyWrench.UP_DAMAGE);
                        });
                    } else {
                        addUpgradeData(this, () -> {
                            upgradeSecondBlock(MonkeyWrench.UP_BLOCK);
                            upgradeDamage(MonkeyWrench.UP_DAMAGE);
                        });
                    }
                } else {
                    if (t.getValue() == 1) {
                        addUpgradeData(this, () -> {
                            upgradeBlock(MonkeyWrench.UP_BLOCK);
                            upgradeSecondDamage(MonkeyWrench.UP_DAMAGE);
                        });
                    } else {
                        addUpgradeData(this, () -> {
                            upgradeBlock(MonkeyWrench.UP_BLOCK);
                            upgradeDamage(MonkeyWrench.UP_DAMAGE);
                        });
                    }
                }
                break;
            case QUICK_BLAST:
                addDamageUpgrade(t.getValue(), t.getFlag());
                break;
            case RUNNING_ENGINE:
                addMagicUpgrade(t.getValue(), t.getFlag());
                break;
            case SCAVENGE_STRIKE:
                addDamageUpgrade(t.getValue(), t.getFlag());
                break;
            case STEAM_ENGINE:
                addMagicUpgrade(t.getValue(), t.getFlag());
                break;
            case PIPE_FENCE:
                addBlockUpgrade(t.getValue(), t.getFlag());
                break;
        }
    }

    public void saveDamage(int amount, boolean secondVar) {
        if (secondVar) {
            info.setDamage2(amount);
            this.baseSecondDamage = this.damage = amount;
        } else {
            info.setDamage(amount);
            this.baseDamage = this.damage = amount;
        }
    }

    public void saveBlock(int amount, boolean secondVar) {
        if (secondVar) {
            info.setBlock2(amount);
            this.baseSecondBlock = this.secondBlock = amount;
        } else {
            info.setBlock(amount);
            this.baseBlock = this.block = amount;
        }
    }

    public void saveMagic(int amount, boolean secondVar) {
        if (secondVar) {
            info.setMagic2(amount);
            this.baseSecondMagic = this.secondMagic = amount;
        } else {
            info.setMagic(amount);
            this.baseMagicNumber = this.magicNumber = amount;
        }
    }

    @Override
    public void onRecreate() {
        for (int i : capturedIndices) {
            MultiUpgradePatches.MultiUpgradeFields.upgradeIndex.set(this, i);
            upgrade();
        }
    }

    @Override
    public SaveInfo onSave() {
        return info;
    }

    @Override
    public void onLoad(SaveInfo saveInfo) {
        if (saveInfo != null) {
            saveInfo.savedCores.forEach(this::addInfo);
            saveDamage(saveInfo.damage, false);
            saveDamage(saveInfo.damage2, true);
            saveBlock(saveInfo.block, false);
            saveBlock(saveInfo.block2, true);
            saveMagic(saveInfo.magic, false);
            saveMagic(saveInfo.magic2, true);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        AssembledCard copy = (AssembledCard) super.makeCopy();
        copy.onLoad(this.onSave());
        return copy;
    }

    @Override
    public void addUpgrades() {}

    public static class SaveInfo {
        public enum CoreType {
            BRASS_SHIELD,
            BURNING_ENGINE,
            DOUBLE_BARREL,
            FLING_SCRAP,
            FLING_SCRAP2,
            FLUX_COMBOBULATOR,
            FLUX_MACHINE,
            MONKEY_WRENCH,
            QUICK_BLAST,
            RUNNING_ENGINE,
            SCAVENGE_STRIKE,
            STEAM_ENGINE,
            PIPE_FENCE
        }

        ArrayList<Triplet<CoreType, Boolean, Integer>> savedCores;

        public void setDamage(int damage) {
            this.damage = damage;
        }

        public void setDamage2(int damage2) {
            this.damage2 = damage2;
        }

        public void setBlock(int block) {
            this.block = block;
        }

        public void setBlock2(int block2) {
            this.block2 = block2;
        }

        public void setMagic(int magic) {
            this.magic = magic;
        }

        public void setMagic2(int magic2) {
            this.magic2 = magic2;
        }

        int damage = -1;
        int damage2 = -1;
        int block = -1;
        int block2 = -1;
        int magic = -1;
        int magic2 = -1;

        public SaveInfo() {
            this.savedCores = new ArrayList<>();
        }

        public SaveInfo(ArrayList<Triplet<CoreType, Boolean, Integer>> coreTypes) {
            this();
            savedCores.addAll(coreTypes);
        }

        public void addCore(Triplet<CoreType, Boolean, Integer> t) {
            savedCores.add(t);
        }

    }
}
