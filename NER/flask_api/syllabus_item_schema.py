from marshmallow import Schema, fields

class SyllabusItemSchema(Schema):
    type = fields.Enum(required=True)
    description = fields.String(required=True)
    weight = fields.Float(required=True)
    due_date = fields.Date(required=True)