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
import {observer} from "mobx-react";
import {Component} from "react";
import React from "react";
import {Address, ContactFull} from "web-api";
import contactDetailsModel from "../model/contactDetailsModel";


@observer
export default class ContactDetailsFrame extends Component {

    renderList(contact: ContactFull) {
        return <div>
            <h3>{contact.firstName} {contact.lastName}</h3>
            <div>
                <span>Number: </span>
                <input
                    type="text"
                    value={contact.number || ""}
                    onChange={e => contactDetailsModel.setNumber(e.target.value)}
                />
            </div>
            <AddressDetails/>
            <div>
                <input
                    type="button"
                    value="Save Contact"
                    onClick={() => contactDetailsModel.updateContact()}
                />
            </div>
        </div>
    }

    render() {
        const contact = contactDetailsModel.contact;
        return <div>
            <h2>Contact Details</h2>
            {contact ? this.renderList(contact) : <div>Select a contact</div>}
        </div>;
    }
}


@observer
class AddressDetails extends Component {

    renderAddress(address: Address) {
        return <div>
            <div>
                <span>Street: </span>
                <input
                    type="text"
                    value={address.street || ""}
                    onChange={e => contactDetailsModel.setAddressField("street", e.target.value)}
                />
            </div>
            <div>
                <span>ZIP: </span>
                <input
                    type="text"
                    value={address.zip || ""}
                    onChange={e => contactDetailsModel.setAddressField("zip", e.target.value)}
                />
            </div>
            <div>
                <span>City: </span>
                <input
                    type="text"
                    value={address.city || ""}
                    onChange={e => contactDetailsModel.setAddressField("city", e.target.value)}
                />
            </div>
            <div>
                <span>Country: </span>
                <input
                    type="text"
                    value={address.country || ""}
                    onChange={e => contactDetailsModel.setAddressField("country", e.target.value)}
                />
            </div>
            <div>
                <input
                    type="button"
                    value="Remove Address"
                    onClick={() => contactDetailsModel.deleteAddress()}
                />
            </div>
        </div>
    }

    render() {
        if (contactDetailsModel.contact?.address) {
            return this.renderAddress(contactDetailsModel.contact?.address)
        } else {
            return <input
                type="button"
                value="Add Address"
                onClick={() => contactDetailsModel.addAddress()}
            />
        }
    }
}