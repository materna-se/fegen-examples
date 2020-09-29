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

import {apiClient, setupFetch, setupTest} from "./util";
import { expect } from "chai";
import {RelTestEntity, User} from "../Entities";

describe("Embedded", () => {

    before(setupFetch)
    beforeEach(setupTest)

    async function getUserOne(): Promise<User> {
        const users = await apiClient.userClient.readAll();
        return users.items.find(u => u.name === "UserOne")!!;
    }

    async function fetchNamed(name: string): Promise<RelTestEntity> {
        const relTestEntities = await apiClient.relTestEntityClient.readAll();
        const result = relTestEntities.items.find(e => e.testString === name);
        if (!result) {
            throw Error("Could not fetch RelTestEntity named " + name);
        }
        return result;
    }

    it("Reads including embedded", async () => {
        const relTestEntities = await apiClient.relTestEntityClient.readAll();

        const nullEmbedded = relTestEntities.items.find(e => e.testString === "embeddedIsNull")!!;
        const nullContent = relTestEntities.items.find(e => e.testString === "embeddedHasNullContent")!!;
        const withContent = relTestEntities.items.find(e => e.testString === "embeddedHasContent")!!;

        expect(nullEmbedded.embedded).to.deep.eq({
            embeddedLong: 8,
            embeddedNullableInt: null,
            embeddedText: "DefaultText"
        });
        expect(nullEmbedded.embeddedNullable).to.be.null;
        expect(nullContent.embeddedNullable).to.be.null;
        expect(withContent.embeddedNullable).to.deep.eq({
            embeddedNullableText: "SomeText",
            otherEmbeddedNullableInt: null
        });
    });

    it("Creates including embedded", async () => {
        const userOne = await getUserOne();
        const embedded = {
            embeddedLong: 64,
            embeddedNullableInt: 32,
            embeddedText: "created"
        };
        await apiClient.relTestEntityClient.create({
            testString: "created",
            oneToOneRequired: userOne,
            manyToOneRequired: userOne,
            embedded,
            embeddedNullable: null,
            manyToMany: [],
            manyToOneOptional: null,
            oneToMany: [],
            oneToOneOptional: null
        });

        const created = await fetchNamed("created");
        expect(created.embedded).to.deep.eq(embedded)
        expect(created.embeddedNullable).to.be.null;
    });

    it("Updates embedded", async () => {
        const embeddedNullable = {
            embeddedNullableText: "NewText",
            otherEmbeddedNullableInt: 42
        };
        const withEmbedded = await fetchNamed("embeddedHasContent");
        withEmbedded.embeddedNullable = embeddedNullable;
        await apiClient.relTestEntityClient.update(withEmbedded);

        const updated = await fetchNamed("embeddedHasContent");
        expect(updated).to.deep.eq({
            ...withEmbedded,
            embeddedNullable
        })
    });

    it("Updates embedded from null", async () => {
        const embeddedNullable = {
            embeddedNullableText: "NewText",
            otherEmbeddedNullableInt: null
        };
        const nullEmbedded = await fetchNamed("embeddedIsNull");
        nullEmbedded.embeddedNullable = embeddedNullable;
        await apiClient.relTestEntityClient.update(nullEmbedded);

        const updated = await fetchNamed("embeddedIsNull");
        expect(updated).to.deep.eq({
            ...nullEmbedded,
            embeddedNullable
        });
    });

    it("Updates embedded to null", async () => {
        const withEmbedded = await fetchNamed("embeddedHasContent");
        withEmbedded.embeddedNullable = null;
        await apiClient.relTestEntityClient.update(withEmbedded);

        const updated = await fetchNamed("embeddedHasContent");
        expect(updated).to.deep.eq({
            ...withEmbedded,
            embeddedNullable: null
        });
    });

    it("Deletes including embedded", async () => {
        const withEmbedded = await fetchNamed("embeddedHasContent");
        await apiClient.relTestEntityClient.delete(withEmbedded);

        const afterDelete = await apiClient.relTestEntityClient.readAll();
        expect(afterDelete.items).to.have.length(2);
    });
})