package Snowpunk.cards.helpers;

import Snowpunk.cards.abstracts.AbstractEasyCard;
import Snowpunk.cards.interfaces.MultiUpgradeCard;
import Snowpunk.patches.TypeOverridePatch;
import Snowpunk.util.AssembledCardArtRoller;
import Snowpunk.util.FormatHelper;
import Snowpunk.util.UpgradeData;
import basemod.helpers.TooltipInfo;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;
import java.util.List;

import static Snowpunk.SnowpunkMod.makeID;

@NoPools @NoCompendium
public class UpgradeAlias extends AbstractEasyCard {
    public static String[] TIP_TEXT = CardCrawlGame.languagePack.getUIString(makeID("Upgrades")).TEXT;
    public final static String ID = makeID(UpgradeAlias.class.getSimpleName());

    private boolean needsArtRefresh = false;
    private UpgradeData data;
    private List<TooltipInfo> tip = new ArrayList<>();
    private AbstractCard baseCard;

    public UpgradeAlias() {
        this(null, "", "");
    }

    public UpgradeAlias(AbstractCard baseCard, String name, String description) {
        super(ID, -1, CardType.SKILL, CardRarity.SPECIAL, CardTarget.NONE);
        this.rawDescription = description;
        this.name = originalName = name;
        initializeTitle();
        initializeDescription();
        //TypeOverridePatch.setOverride(this, GameDictionary.UPGRADE.NAMES[0]);
        TypeOverridePatch.setOverride(this, TIP_TEXT[0]);
        if (CardLibrary.getAllCards() != null && !CardLibrary.getAllCards().isEmpty()) {
            AssembledCardArtRoller.computeCard(this);
        } else {
            needsArtRefresh = true;
        }
        this.baseCard = baseCard;
    }

    @Override
    public List<TooltipInfo> getCustomTooltipsTop() {
        return tip;
    }

    public void registerUpgradeData(UpgradeData data) {
        if (baseCard == null) {
            return;
        }
        this.data = data;
        StringBuilder sb = new StringBuilder();
        sb.append(TIP_TEXT[1]).append(baseCard.name);
        if (!data.dependencies.isEmpty()) {
            if (data.strict) {
                sb.append(TIP_TEXT[2]);
            } else {
                sb.append(TIP_TEXT[3]);
            }
            for (int i : data.dependencies) {
                UpgradeData dep = ((MultiUpgradeCard)baseCard).getUpgrades(baseCard).get(i);
                String prefix = "";
                if (dep.applied) {
                    prefix = "#g";
                }
                if (dep.alias == null) {
                    sb.append(prefix).append("- ").append(prefix).append(TIP_TEXT[0]).append(" ").append(prefix).append(i);
                } else {
                    sb.append(prefix).append("- ").append(FormatHelper.prefixWords(dep.alias.name, prefix));
                }
                sb.append(" NL ");
            }
        }
        if (!data.exclusions.isEmpty()) {
            sb.append(TIP_TEXT[4]);
            String prefix = "";
            if (data.applied) {
                prefix = "#r";
            }
            for (int i : data.exclusions) {
                UpgradeData excludes = ((MultiUpgradeCard)baseCard).getUpgrades(baseCard).get(i);
                if (excludes.alias == null) {
                    sb.append(prefix).append("- ").append(prefix).append(TIP_TEXT[0]).append(" ").append(prefix).append(i);
                } else {
                    sb.append(prefix).append("- ").append(FormatHelper.prefixWords(excludes.alias.name, prefix));
                }
                sb.append(" NL ");
            }
        }
        String s = sb.toString();
        if (s.endsWith(" NL ")); {
            s = s.substring(0, s.length()-4);
        }
        tip.add(new TooltipInfo(TIP_TEXT[0], s));
    }

    @Override
    protected Texture getPortraitImage() {
        return AssembledCardArtRoller.getPortraitTexture(this);
    }

    @Override
    public AbstractCard makeCopy() {
        return new UpgradeAlias(this.baseCard, this.name, this.rawDescription);
    }

    @Override
    public void update() {
        super.update();
        if (needsArtRefresh) {
            AssembledCardArtRoller.computeCard(this);
        }
    }

    public void use(AbstractPlayer p, AbstractMonster m) {}

    @Override
    public void upp() {}
}