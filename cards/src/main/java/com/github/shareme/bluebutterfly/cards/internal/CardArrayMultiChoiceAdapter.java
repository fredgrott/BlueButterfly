/*
 * ******************************************************************************
 *   Copyright (c) 2013-2014 Gabriele Mariotti.
 *   Modifications Copyright(C) 2016 Fred Grott(GrottWorkShop)
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *  *****************************************************************************
 */

package com.github.shareme.bluebutterfly.cards.internal;

import android.app.Activity;
import android.content.Context;
import android.view.ActionMode;
import android.view.Menu;
import android.view.View;
import android.widget.AbsListView;

import com.github.shareme.bluebutterfly.cards.internal.multichoice.DefaultOptionMultiChoice;
import com.github.shareme.bluebutterfly.cards.internal.multichoice.MultiChoiceAdapter;
import com.github.shareme.bluebutterfly.cards.internal.multichoice.MultiChoiceAdapterHelperBase;
import com.github.shareme.bluebutterfly.cards.internal.multichoice.OptionMultiChoice;
import com.github.shareme.bluebutterfly.cards.view.CardListView;
import com.github.shareme.bluebutterfly.cards.view.base.CardViewWrapper;

import java.util.ArrayList;
import java.util.List;




/**
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
@SuppressWarnings("unused")
public abstract class CardArrayMultiChoiceAdapter extends CardArrayAdapter implements MultiChoiceAdapter, AbsListView.MultiChoiceModeListener {

    /**
     * Helper
     */
    private MultiChoiceAdapterHelperBase mHelper = new MultiChoiceAdapterHelperBase(this);


    /**
     * Option for multichoice
     */
    protected OptionMultiChoice mOptions;

    // -------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------

    /**
     * Constructor
     *
     * @param context The current context.
     * @param cards   The cards to represent in the ListView.
     */
    public CardArrayMultiChoiceAdapter(Context context, List<Card> cards) {
        this(context, cards, new DefaultOptionMultiChoice());
    }

    /**
     * Constructor
     *
     * @param context The current context.
     * @param cards   The cards to represent in the ListView.
     */
    public CardArrayMultiChoiceAdapter(Context context, List<Card> cards, OptionMultiChoice options) {
        super(context, cards);
        this.mOptions = options;
        mHelper.setMultiChoiceModeListener(this);
    }

    // -------------------------------------------------------------
    // Adapter
    // -------------------------------------------------------------

    @Override
    public void setCardListView(CardListView cardListView) {
        super.setCardListView(cardListView);
        mHelper.setAdapterView(cardListView);
    }

    /**
     * Used to setup some element events for multichoice
     *
     * @param view
     * @param mCard
     * @param mCardView
     * @param position
     */
    @Override
    protected void setupMultichoice(View view, Card mCard, CardViewWrapper mCardView, long position) {
        super.setupMultichoice(view, mCard, mCardView, position);
        mHelper.setupMultichoice(view, mCard, mCardView, position);
    }


    @Override
    public Card getItem(int position) {
        Card card = super.getItem(position);
        card.mMultiChoiceEnabled = true;
        return card;
    }

    // -------------------------------------------------------------
    // ActionMode
    // -------------------------------------------------------------

    public boolean startActionMode(Activity activity) {
        return mHelper.startActionMode(activity);
    }

    /**
     * Called when action mode is first created. The menu supplied will be used to
     * generate action buttons for the action mode.
     *
     * @param mode ActionMode being created
     * @param menu Menu used to populate action buttons
     * @return true if the action mode should be created, false if entering this
     *              mode should be aborted.
     */
    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
       return mHelper.onCreateActionMode(mode,menu);
    }


    /**
     * Called when an action mode is about to be exited and destroyed.
     *
     * @param mode The current ActionMode being destroyed
     */
    @Override
    public void onDestroyActionMode(ActionMode mode) {
       mHelper.onDestroyActionMode(mode);
    }

    /**
     * Called when an item is checked or unchecked during selection mode.
     *
     * @param mode The {@link ActionMode} providing the selection mode
     * @param position Adapter position of the item that was checked or unchecked
     * @param id Adapter ID of the item that was checked or unchecked
     * @param checked <code>true</code> if the item is now checked, <code>false</code>
     *                if the item is now unchecked.
     */
    @Override
    public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
       mHelper.onItemCheckedStateChanged(mode,position,id,checked);
    }

    /**
     * Indicate if action mode is started
     *
     * @return
     */
    @Override
    public boolean isActionModeStarted() {
        return mHelper.isActionModeStarted();
    }

    // -------------------------------------------------------------
    // MultiChoice
    // -------------------------------------------------------------

    /**
     * Returns the selected cards
     * @return
     */
    protected ArrayList<Card>  getSelectedCards() {
        return mHelper.getSelectedCards();
    }

    @Override
    public OptionMultiChoice getOptionMultiChoice() {
        return mOptions;
    }
}
