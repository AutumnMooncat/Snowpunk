package Snowpunk.cards.cores;

import Snowpunk.cards.abstracts.AbstractEasyCard;
import Snowpunk.util.AssembledCardArtRoller;
import Snowpunk.util.CardArtRoller;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;
import java.util.function.Function;

import static Snowpunk.SnowpunkMod.makeID;

@NoPools @NoCompendium
public class AssembledCard extends AbstractEasyCard {
    public static final String ID = makeID(AssembledCard.class.getSimpleName());

    private final ArrayList<Function<AbstractCard, Void>> onUpgradeFunctions = new ArrayList<>();

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
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {}

    public void addUpgradeFunction(Function<AbstractCard, Void> f) {
        onUpgradeFunctions.add(f);
    }

    @Override
    public void upp() {
        for (Function<AbstractCard, Void> f : onUpgradeFunctions) {
            f.apply(this);
        }
    }
}
