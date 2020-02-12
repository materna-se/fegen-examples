/**
 * Copyright 2020 Materna Information & Communications SE
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
import {Component} from "react";
import {observer} from "mobx-react";
import {action, observable} from "mobx";
import React from "react";
import regexSearchModel from "../model/regexSearchModel";
import {fromPromise} from "mobx-utils";
import ContactEntry from "./ContactEntry";

@observer
export default class RegexSearchFrame extends Component {

    @observable
    searchText: string = "";

    @action
    setSearchText(text: string) {
        this.searchText = text;
    }

    render() {
        return <div>
            <h2>Regex Search</h2>
            <div>
                <input
                    type="text"
                    value={this.searchText}
                    onChange={e => this.setSearchText(e.target.value)}
                />
                <input
                    type="button"
                    value="Regex search"
                    onClick={() => regexSearchModel.search(this.searchText)}
                />
            </div>
            {fromPromise(regexSearchModel.searchResults).case({
                "pending": () => <div>Loading</div>,
                "rejected": () => <div>Failed to load</div>,
                "fulfilled": contacts => <div>
                    {contacts.map(contact => <ContactEntry key={contact.id} contact={contact}/>)}
                </div>
            })}
        </div>
    }
}
