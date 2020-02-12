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
import React, {Component} from "react";
import {observer} from "mobx-react";
import userContactsModel from "../model/userContactsModel";
import {action, observable, runInAction} from "mobx";
import ContactEntry from "./ContactEntry";


@observer
export default class UserContactsFrame extends Component {

    render() {
        return <div>
            <h2>User Contacts</h2>
            <UserCreator/>
            <UserList/>
        </div>
    }
}

@observer
class UserList extends Component {

    header(): string {
        if (userContactsModel.username) {
            return `Contacts for user ${userContactsModel.username}`
        } else {
            return `Select a user`
        }
    }

    render() {
        return <div>
            <div>{this.header()}</div>
            <div>
                {(userContactsModel.contacts || []).map(contact => <ContactEntry key={contact.id} contact={contact}/>)}
            </div>
        </div>
    }

}

@observer
class UserCreator extends Component {

    @observable newContactFirstName: string = "";

    @observable newContactLastName: string = "";

    @action
    changeNewContactFirstName(firstName: string) {
        this.newContactFirstName = firstName;
    }

    @action
    changeNewContactLastName(lastName: string) {
        this.newContactLastName = lastName;
    }

    async createContact() {
        await userContactsModel.createContact(this.newContactFirstName, this.newContactLastName);
        runInAction("Reset new contact", () => {
            this.newContactFirstName = "";
            this.newContactLastName = "";
        });
    }

    render() {
        return <div>
            <div>
                <input
                    type="text"
                    value={this.newContactFirstName}
                    onChange={e => this.changeNewContactFirstName(e.target.value)}
                />
                <input
                    type="text"
                    value={this.newContactLastName}
                    onChange={e => this.changeNewContactLastName(e.target.value)}
                />
                <input
                    type="button"
                    onClick={this.createContact.bind(this)}
                    value="Create contact"
                />
            </div>
        </div>
    }
}
