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
import {action, observable} from "mobx";
import {fromPromise} from "mobx-utils";
import React, {Component} from "react";
import contactSearchModel from "../model/contactSearchModel";
import {observer} from "mobx-react";
import ContactEntry from "./ContactEntry";

@observer
export default class ContactSearchFrame extends Component {

    @observable filterText: string = "";

    @action
    setFilterText(text: string) {
        this.filterText = text;
    }

    render() {
        return <div>
            <h2>Contact Search</h2>
            <div>
                <input
                    type="text"
                    value={this.filterText}
                    onChange={e => this.setFilterText(e.target.value)}
                />
                <input
                    type="button"
                    value="Search"
                    onClick={() => contactSearchModel.setFilterText(this.filterText)}
                />
            </div>
            <div>
                {fromPromise(contactSearchModel.contacts).case({
                    "pending": () => <div>Loading</div>,
                    "rejected": () => <div>Failed to load</div>,
                    "fulfilled": contacts => <div>
                        {contacts.items.map(contact => <ContactEntry key={contact.id} contact={contact}/>)}
                    </div>
                })}
            </div>
            <Paginator/>
        </div>
    }
}

@observer
class Paginator extends Component {

    render() {
        return fromPromise(contactSearchModel.contacts).case({
            rejected: () => <div />,
            pending: () => <div />,
            fulfilled: response => {
                const currentPage = contactSearchModel.page;
                const totalPages = response.page.totalPages;
                if (totalPages === 0) {
                    return <div>No results</div>
                }
                return <div>
                    <input
                        type="button"
                        value="<"
                        disabled={currentPage === 0}
                        onClick={() => contactSearchModel.setPage(currentPage - 1)}
                        />
                    {Array.from(Array(totalPages).keys()).map((n: number) => {
                        return <input
                            type="button"
                            value={n+1}
                            disabled={currentPage === n}
                            onClick={() => contactSearchModel.setPage(n)}
                        />
                    })}
                    <input
                        type="button"
                        value=">"
                        disabled={currentPage === totalPages - 1}
                        onClick={() => contactSearchModel.setPage(currentPage + 1)}
                    />
                </div>
            }
        })
    }
}