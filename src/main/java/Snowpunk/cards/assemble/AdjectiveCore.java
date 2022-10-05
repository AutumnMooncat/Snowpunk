package Snowpunk.cards.assemble;

import Snowpunk.util.AssembledCardArtRoller;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;

import java.util.ArrayList;
import java.util.Random;

import static Snowpunk.SnowpunkMod.makeID;


@NoPools
@NoCompendium
public class AdjectiveCore extends CoreCard {
    public static final String ID = makeID(AdjectiveCore.class.getSimpleName());
    public static String[] TEXT = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION;

    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = -2;

    public AdjectiveCore() {
        this(getRand());
    }

    public AdjectiveCore(int index) {
        super(ID, COST, TYPE, EffectTag.ADJECTIVE);
        misc = index;
        initializeDescription();
    }

    @Override
    protected Texture getPortraitImage() {
        return AssembledCardArtRoller.getPortraitTexture(this);
    }

    @Override
    public void initializeDescription() {
        rawDescription = TEXT[misc];
        name = rawDescription;
        if (CardLibrary.getAllCards() != null && !CardLibrary.getAllCards().isEmpty()) {
            if (!name.equals("") && name != null)
                AssembledCardArtRoller.computeCard(this);
        }
        super.initializeDescription();
    }

    @Override
    public boolean getCustomCANTSpawnCondition(ArrayList<CoreCard> coreCards) {
        return true;
    }

    private static int getRand() {
        Random random = new Random();
        return random.nextInt(TEXT.length);
    }

    @Override
    public AbstractCard makeCopy() {
        return new AdjectiveCore(misc);
    }
}
